document.addEventListener('DOMContentLoaded', () => {
    const resetPasswordForm = document.getElementById('resetPasswordForm');
    const newPasswordInput = document.getElementById('newPassword');
    const confirmPasswordInput = document.getElementById('confirmPassword');
    const passwordMessage = document.getElementById('passwordMessage');
    const passwordRuleMessage = document.getElementById('passwordRuleMessage');

    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*\W)[A-Za-z\d\W]{6,20}$/;

    function checkPasswordRule() {
        const password = newPasswordInput.value;

        if (passwordPattern.test(password)) {
            passwordRuleMessage.textContent = '';
        } else {
            passwordRuleMessage.textContent = '특수문자, 대문자, 소문자 포함 6~20자리를 입력해주세요.';
        }
    }

    function checkPasswordMatch() {
        const password = newPasswordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        if (password === confirmPassword && password !== '') {
            passwordMessage.textContent = '비밀번호가 일치합니다.';
            passwordMessage.className = 'info-message success';
        } else {
            passwordMessage.textContent = '비밀번호가 일치하지 않습니다.';
            passwordMessage.className = 'info-message error';
        }
    }

    newPasswordInput.addEventListener('input', checkPasswordRule);
    newPasswordInput.addEventListener('input', checkPasswordMatch);
    confirmPasswordInput.addEventListener('input', checkPasswordMatch);

    resetPasswordForm.addEventListener('submit', (event) => {
        const password = newPasswordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        if (!passwordPattern.test(password)) {
            alert('비밀번호는 특수문자, 대문자, 소문자 포함 6~20자리를 입력해야 합니다.');
            event.preventDefault();
        }

        if (password !== confirmPassword) {
            alert('비밀번호가 일치하지 않습니다.');
            event.preventDefault();
        }
    });
});
