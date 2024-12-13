document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');
    const userListBody = document.getElementById('userListBody');
    const userListPagination = document.getElementById('userListPagination');
    const userDetailModal = new bootstrap.Modal('#userDetailModal');

    let currentPage = 0;
    let currentSearchTerm = '';

    // 회원 목록 로드 (페이징 처리)
    async function loadUserList(page = 0, searchTerm = '') {
        try {
            let url = searchTerm
                ? `/admin/users/search?searchTerm=${searchTerm}&page=${page}&size=10`
                : `/admin/users/list?page=${page}&size=10`;

            const response = await fetch(url);
            const pageData = await response.json();
            const users = pageData.content;

            // 사용자 목록 렌더링
            userListBody.innerHTML = users.map(user => `
                <tr data-user-id="${user.id}">
                    <td>${user.nickname}</td>
                    <td>${user.name}</td>
                    <td>${new Date(user.joinDate).toLocaleDateString()}</td>
                    <td>${user.deleted ? '탈퇴' : '정상'}</td>
                </tr>
            `).join('');

            // 페이지네이션 렌더링
            renderPagination(pageData);

            // 각 행 클릭 이벤트 추가
            userListBody.querySelectorAll('tr').forEach(row => {
                row.addEventListener('click', () => showUserDetail(row.dataset.userId));
            });

            currentPage = pageData.number;
            currentSearchTerm = searchTerm;
        } catch (error) {
            console.error('회원 목록 로드 실패:', error);
        }
    }

    // 페이지네이션 렌더링
    function renderPagination(pageData) {
        userListPagination.innerHTML = '';

        // 이전 페이지 버튼
        const prevButton = document.createElement('li');
        prevButton.classList.add('page-item', pageData.first ? 'disabled' : '');
        prevButton.innerHTML = `
            <a class="page-link" href="#" ${pageData.first ? 'tabindex="-1"' : ''}>
                이전
            </a>
        `;
        if (!pageData.first) {
            prevButton.querySelector('a').addEventListener('click', (e) => {
                e.preventDefault();
                loadUserList(currentPage - 1, currentSearchTerm);
            });
        }
        userListPagination.appendChild(prevButton);

        // 페이지 번호 버튼
        for (let i = 0; i < pageData.totalPages; i++) {
            const pageButton = document.createElement('li');
            pageButton.classList.add('page-item', pageData.number === i ? 'active' : '');
            pageButton.innerHTML = `
                <a class="page-link" href="#">${i + 1}</a>
            `;
            pageButton.addEventListener('click', (e) => {
                e.preventDefault();
                loadUserList(i, currentSearchTerm);
            });
            userListPagination.appendChild(pageButton);
        }

        // 다음 페이지 버튼
        const nextButton = document.createElement('li');
        nextButton.classList.add('page-item', pageData.last ? 'disabled' : '');
        nextButton.innerHTML = `
            <a class="page-link" href="#" ${pageData.last ? 'tabindex="-1"' : ''}>
                다음
            </a>
        `;
        if (!pageData.last) {
            nextButton.querySelector('a').addEventListener('click', (e) => {
                e.preventDefault();
                loadUserList(currentPage + 1, currentSearchTerm);
            });
        }
        userListPagination.appendChild(nextButton);
    }

    // 검색 버튼 이벤트
    searchButton.addEventListener('click', () => {
        const searchTerm = searchInput.value.trim();
        if (searchTerm) {
            loadUserList(0, searchTerm);
        } else {
            loadUserList(); // 검색어 없으면 전체 목록
        }
    });

    // 회원 상세 정보 모달
    async function showUserDetail(userId) {
        try {
            const response = await fetch(`/admin/users/${userId}`);
            const user = await response.json();

            document.getElementById('modalUserId').textContent = user.id;
            document.getElementById('modalUserEmail').textContent = user.email;
            document.getElementById('modalUserName').textContent = user.name;
            document.getElementById('modalUserNickname').textContent = user.nickname;
            document.getElementById('modalUserJoinDate').textContent =
                new Date(user.joinDate).toLocaleDateString();
            document.getElementById('modalUserRole').textContent = user.role;

            const toggleDeleteButton = document.getElementById('toggleDeleteButton');
            toggleDeleteButton.textContent = user.deleted ? '탈퇴 복구' : '회원 탈퇴';
            toggleDeleteButton.onclick = () => toggleUserDelete(user.id);

            userDetailModal.show();
        } catch (error) {
            console.error('회원 상세 정보 로드 실패:', error);
        }
    }

    // 회원 탈퇴/복구
    async function toggleUserDelete(userId) {
        try {
            await fetch(`/admin/users/${userId}/toggle-delete`, { method: 'POST' });
            userDetailModal.hide();
            loadUserList();
        } catch (error) {
            console.error('회원 탈퇴/복구 실패:', error);
        }
    }

    // 페이지 로드 시 초기화
    loadUserList();
});