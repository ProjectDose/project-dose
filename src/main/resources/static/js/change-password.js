document.addEventListener('DOMContentLoaded', () => {
    const userId = document.body.getAttribute('data-user-id');

    if (!userId) {
        alert('사용자 ID를 찾을 수 없습니다. 다시 로그인 해주세요.');
        window.location.href = '/logout';
        return;
    }

    const changeButton = document.getElementById('change-password-btn');
    changeButton.addEventListener('click', async () => {
        const currentPassword = document.getElementById('current-password').value.trim();
        const newPassword = document.getElementById('new-password').value.trim();
        const confirmPassword = document.getElementById('confirm-password').value.trim();
        const errorMessageElement = document.getElementById('error-message');

        if (!currentPassword || !newPassword || !confirmPassword) {
            errorMessageElement.textContent = '모든 필드를 입력해주세요.';
            return;
        }

        if (newPassword !== confirmPassword) {
            errorMessageElement.textContent = '새 비밀번호와 확인 비밀번호가 일치하지 않습니다.';
            return;
        }

        try {
            const response = await fetch(`/api/users/${userId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ currentPassword, newPassword })
            });

            const data = await response.json();

            if (!response.ok) {
                errorMessageElement.textContent = data.message || '비밀번호 변경에 실패했습니다.';
            } else {
                alert('비밀번호가 변경되었습니다.');
                window.location.href = '/mypage';
            }
        } catch (error) {
            errorMessageElement.textContent = '서버와의 통신 중 오류가 발생했습니다.';
        }
    });
});
