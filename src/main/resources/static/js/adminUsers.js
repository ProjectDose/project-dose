document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');
    const userListBody = document.getElementById('userListBody');
    const userListPagination = document.getElementById('userListPagination');
    const userDetailModal = new bootstrap.Modal('#userDetailModal');
    const passwordResetModal = new bootstrap.Modal('#passwordResetModal');

    const modalUserId = document.getElementById('modalUserId');
    const modalUserEmail = document.getElementById('modalUserEmail');
    const modalUserName = document.getElementById('modalUserName');
    const modalUserNickname = document.getElementById('modalUserNickname');
    const modalUserJoinDate = document.getElementById('modalUserJoinDate');
    const modalUserRole = document.getElementById('modalUserRole');
    const modalUserDeleted = document.getElementById('modalUserDeleted');
    const toggleDeleteButton = document.getElementById('toggleDeleteButton');
    const resetPasswordButton = document.getElementById('resetPasswordButton');
    const tempPasswordDisplay = document.getElementById('tempPasswordDisplay');

    let currentPage = 0;
    let currentSearchTerm = '';

    // 회원 목록 로드 (페이징 처리)
    async function loadUserList(page = 0, searchTerm = '') {
        try {
            // URL을 컨트롤러 매핑에 맞게 수정
            let url = searchTerm
                ? `/api/admin/search?searchTerm=${searchTerm}&page=${page}&size=10`
                : `/api/admin/list?page=${page}&size=10`;

            const response = await fetch(url);
            const responseData = await response.json();

            console.log('Response Data:', responseData);

            // 응답 구조에 맞게 데이터 추출
            const users = responseData.content || [];
            const pageInfo = {
                totalPages: responseData.totalPages || 0,
                number: responseData.number || 0,
                first: responseData.first || true,
                last: responseData.last || true
            };

            // 사용자 목록 렌더링 (기존 코드와 동일)
            userListBody.innerHTML = users.map(user => `
            <tr data-user-id="${user.id}">
                <td>${user.nickname || '미설정'}</td>
                <td>${user.name || '미설정'}</td>
                <td>${user.joinDate ? new Date(user.joinDate).toLocaleDateString() : '미설정'}</td>
                <td>${user.deleted ? '탈퇴' : '정상'}</td>
            </tr>
        `).join('');

            // 페이지네이션 렌더링
            renderPagination(pageInfo);

            // 각 행 클릭 이벤트 추가
            userListBody.querySelectorAll('tr').forEach(row => {
                row.addEventListener('click', () => showUserDetail(row.dataset.userId));
            });

            currentPage = pageInfo.number;
            currentSearchTerm = searchTerm;

        } catch (error) {
            console.error('회원 목록 로드 실패:', error);
            userListBody.innerHTML = `
            <tr>
                <td colspan="4" class="text-center text-danger">
                    데이터를 로드할 수 없습니다. 잠시 후 다시 시도해주세요.
                </td>
            </tr>
        `;
        }
    }

    // 페이지네이션 렌더링
    function renderPagination(pageData) {
        userListPagination.innerHTML = '';

        // pageData의 존재 여부와 속성 확인
        if (!pageData || typeof pageData !== 'object') {
            console.error('페이지 데이터가 유효하지 않습니다.');
            return;
        }

        const totalPages = pageData.totalPages || 0;
        const currentPage = pageData.number || 0;
        const isFirst = pageData.first || true;
        const isLast = pageData.last || true;

        // 이전 페이지 버튼
        const prevButton = document.createElement('li');
        prevButton.classList.add('page-item', isFirst ? 'disabled' : '');
        prevButton.innerHTML = `
            <a class="page-link" href="#" ${isFirst ? 'tabindex="-1"' : ''}>
                이전
            </a>
        `;
        if (!isFirst) {
            prevButton.querySelector('a').addEventListener('click', (e) => {
                e.preventDefault();
                loadUserList(currentPage - 1, currentSearchTerm);
            });
        }
        userListPagination.appendChild(prevButton);

        // 페이지 번호 버튼
        for (let i = 0; i < totalPages; i++) {
            const pageButton = document.createElement('li');
            pageButton.classList.add('page-item', currentPage === i ? 'active' : '');
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
        nextButton.classList.add('page-item', isLast ? 'disabled' : '');
        nextButton.innerHTML = `
            <a class="page-link" href="#" ${isLast ? 'tabindex="-1"' : ''}>
                다음
            </a>
        `;
        if (!isLast) {
            nextButton.querySelector('a').addEventListener('click', (e) => {
                e.preventDefault();
                loadUserList(currentPage + 1, currentSearchTerm);
            });
        }
        userListPagination.appendChild(nextButton);
    }

    // 회원 상세 정보 모달
    async function showUserDetail(userId) {
        try {
            const response = await fetch(`/api/admin/${userId}`);
            const user = await response.json();

            modalUserId.textContent = user.id;
            modalUserEmail.textContent = user.email;
            modalUserName.textContent = user.name;
            modalUserNickname.textContent = user.nickname;
            modalUserJoinDate.textContent =
                new Date(user.joinDate).toLocaleDateString();
            modalUserRole.textContent = user.role;
            modalUserDeleted.textContent = user.deleted ? '탈퇴' : '정상';

            toggleDeleteButton.textContent = user.deleted ? '탈퇴 복구' : '회원 탈퇴';
            toggleDeleteButton.onclick = () => toggleUserDelete(user.id);

            userDetailModal.show();
        } catch (error) {
            console.error('회원 상세 정보 로드 실패:', error);
            alert('회원 정보를 불러오는 데 실패했습니다.');
        }
    }

    // 회원 탈퇴/복구
    async function toggleUserDelete(userId) {
        try {
            await fetch(`/api/admin/${userId}/toggle-delete`, {method: 'POST'});
            userDetailModal.hide();
            loadUserList(currentPage, currentSearchTerm);
        } catch (error) {
            console.error('회원 탈퇴/복구 실패:', error);
            alert('회원 탈퇴/복구에 실패했습니다.');
        }
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

    // 비밀번호 초기화 버튼 이벤트
    resetPasswordButton.addEventListener('click', async () => {
        const userId = modalUserId.textContent;
        const newPassword = generateTemporaryPassword();

        try {
            const response = await fetch(`/api/admin/${userId}/reset-password`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    password: newPassword
                })
            });

            if (response.ok) {
                // 임시 비밀번호 모달에 비밀번호 표시
                tempPasswordDisplay.textContent = newPassword;

                // 기존 모달 숨기기
                userDetailModal.hide();

                // 새 비밀번호 모달 표시
                passwordResetModal.show();
            } else {
                alert('비밀번호 초기화에 실패했습니다.');
            }
        } catch (error) {
            console.error('비밀번호 초기화 실패:', error);
            alert('비밀번호 초기화 중 오류가 발생했습니다.');
        }
    });

    // 안전한 임시 비밀번호 생성 함수
    function generateTemporaryPassword() {
        const prefix = 'dose';
        const numbers = Math.floor(1000 + Math.random() * 9000); // 4자리 숫자
        const specialChars = '!@#$%^&*()';
        const specialChar = specialChars[Math.floor(Math.random() * specialChars.length)];

        return `${prefix}${numbers}${specialChar}`;
    }

    // 페이지 로드 시 초기화 (첫 페이지 로드)
    loadUserList();
});