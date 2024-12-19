document.addEventListener('DOMContentLoaded', () => {
    const userId = document.body.getAttribute('data-user-id');

    if (!userId) {
        alert('사용자 ID를 찾을 수 없습니다. 다시 로그인 해주세요.');
        window.location.href = '/logout';
        return;
    }

    const changeButton = document.getElementById('change-nickname-btn');
    changeButton.addEventListener('click', async () => {
        const newNickname = document.getElementById('new-nickname').value.trim();
        const errorMessageElement = document.getElementById('error-message');

        if (!newNickname) {
            errorMessageElement.textContent = '닉네임을 입력해주세요.';
            return;
        }

        try {
            const response = await fetch(`/api/users/${userId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json',
                    'Accept': 'application/json'},
                body: JSON.stringify({ nickname: newNickname })
            });

            const data = await response.json();

            if (!response.ok) {
                errorMessageElement.textContent = data.message || '닉네임 변경에 실패했습니다.';
            } else {
                alert('닉네임이 변경되었습니다.');
                window.location.href = '/mypage';
            }
        } catch (error) {
            errorMessageElement.textContent = '오류가 발생했습니다.';
        }
    });
});

