document.addEventListener('DOMContentLoaded', () => {
    const nicknameInput = document.getElementById('nickname');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const passwordConfirmInput = document.getElementById('passwordConfirm');
    const nicknameMessage = document.getElementById('nicknameMessage');
    const emailMessage = document.getElementById('emailMessage');
    const passwordMessage = document.getElementById('passwordMessage');
    const passwordRuleMessage = document.getElementById('passwordRuleMessage');

    document.getElementById('checkNicknameButton').addEventListener('click', () => {
        const nickname = nicknameInput.value.trim();
        if (!nickname) {
            nicknameMessage.textContent = '닉네임을 입력해주세요.';
            nicknameMessage.className = 'info-message error';
            return;
        }

        fetch(`/api/auth/checkNicknameDuplicate?nickname=${encodeURIComponent(nickname)}`)
            .then(response => response.json())
            .then(data => {
                nicknameMessage.textContent = data.message;
                nicknameMessage.className = data.message.includes('사용 가능한') ? 'info-message success' : 'info-message error';
            });
    });

    document.getElementById('checkEmailButton').addEventListener('click', () => {
        const email = emailInput.value.trim();
        if (!email) {
            emailMessage.textContent = '이메일을 입력해주세요.';
            emailMessage.className = 'info-message error';
            return;
        }

        fetch(`/api/auth/checkEmailDuplicate?email=${encodeURIComponent(email)}`)
            .then(response => response.json())
            .then(data => {
                emailMessage.textContent = data.message;
                emailMessage.className = data.message.includes('사용 가능한') ? 'info-message success' : 'info-message error';
            });
    });

    // 비밀번호 규칙 표시
    passwordInput.addEventListener('focus', () => {
        passwordRuleMessage.style.display = 'block';
    });

    passwordInput.addEventListener('blur', () => {
        passwordRuleMessage.style.display = 'none';
    });

    // 비밀번호 확인
    function checkPasswordMatch() {
        const password = passwordInput.value;
        const confirmPassword = passwordConfirmInput.value;

        if (password === confirmPassword && password !== '') {
            passwordMessage.textContent = '비밀번호가 일치합니다.';
            passwordMessage.className = 'info-message success';
        } else {
            passwordMessage.textContent = '비밀번호가 일치하지 않습니다.';
            passwordMessage.className = 'info-message error';
        }
    }

    passwordInput.addEventListener('input', checkPasswordMatch);
    passwordConfirmInput.addEventListener('input', checkPasswordMatch);
});
