document.addEventListener('DOMContentLoaded', () => {
    const userId = document.body.getAttribute('data-user-id');

    if (!userId) {
        alert('사용자 ID를 찾을 수 없습니다. 다시 로그인 해주세요.');
        return;
    }

    const deleteButton = document.getElementById('delete-account-btn');

    if (!deleteButton) {
        alert('회원 탈퇴 버튼이 존재하지 않습니다.');
        return;
    }

    deleteButton.addEventListener('click', async () => {
        const confirmation = confirm('정말로 회원 탈퇴를 하시겠습니까?');
        if (!confirmation) return;

        try {
            const response = await fetch(`/api/users/${userId}`, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' }
            });

            const data = await response.json();

            if (!response.ok) {
                alert(data.message || '회원 탈퇴에 실패했습니다.');
            } else {
                alert('회원 탈퇴가 완료되었습니다.');
                window.location.href = '/auth/login';
            }
        } catch (error) {
            alert('오류가 발생했습니다.');
        }
    });
});

