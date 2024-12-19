document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.querySelector('form');
    const keepLoggedInCheckbox = document.getElementById('keepLoggedIn');
    const resetEmailInput = document.getElementById('resetEmail');
    const resetMessage = document.getElementById('resetMessage');

    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const formData = new FormData(loginForm);

        if (!window.currentToken) {
            alert("FCM 토큰 발급 오류: 알림 권한을 확인해주세요.");
            return;
        }

        try {
            const loginResponse = await fetch('/auth/login', {
                method: 'POST',
                body: formData
            });

            if (loginResponse.ok) {
                await fetch('/api/v1/device-token/register', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        email: formData.get('email'),
                        token: window.currentToken
                    })
                });
                window.location.href = '/home';
            } else {
                alert('잘못된 이메일 또는 비밀번호입니다.');
            }
        } catch (error) {
            console.error('로그인 또는 토큰 등록 오류:', error);
            alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
        }
    });

    window.openPasswordResetModal = () => {
        const passwordResetModal = new bootstrap.Modal(document.getElementById('passwordResetModal'));
        passwordResetModal.show();
    };

    window.sendPasswordReset = () => {
        const email = resetEmailInput.value.trim();

        if (!email) {
            resetMessage.textContent = "이메일을 입력해주세요.";
            resetMessage.style.display = 'block';
            resetMessage.style.color = 'red';
            return;
        }

        fetch('/api/auth/findPassword', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email })
        })
            .then(response => {
                if (response.ok) {
                    alert("비밀번호 재설정 링크를 이메일로 보냈습니다.");
                    resetEmailInput.value = '';
                    resetMessage.style.display = 'none';
                    document.getElementById('passwordResetModal').click();
                } else {
                    resetMessage.textContent = "가입된 이메일이 아닙니다.";
                    resetMessage.style.display = 'block';
                    resetMessage.style.color = 'red';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                resetMessage.textContent = "서버 오류가 발생했습니다.";
                resetMessage.style.display = 'block';
                resetMessage.style.color = 'red';
            });
    };

    keepLoggedInCheckbox.addEventListener('change', () => {
        console.log(`로그인 유지 상태: ${keepLoggedInCheckbox.checked}`);
    });
});
