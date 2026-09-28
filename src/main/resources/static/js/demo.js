const fullNameInput = document.querySelector('#demo-full-name');
const emailInput = document.querySelector('#demo-email');
const passwordInput = document.querySelector('#demo-password');
const output = document.querySelector('#demo-output');
const buttons = document.querySelectorAll('#demo-form button');

function showResult(status, data) {
    output.textContent = JSON.stringify({ status, data }, null, 2);
}

async function sendRequest(url, options) {
    buttons.forEach(button => button.disabled = true);
    try {
        const response = await api(url, options);
        const body = await response.text();
        let data;
        try {
            data = body ? JSON.parse(body) : null;
        } catch {
            data = body;
        }
        showResult(response.status, data);
        return { response, data };
    } catch (error) {
        showResult(null, { message: 'Không kết nối được tới máy chủ', detail: error.message });
        return null;
    } finally {
        buttons.forEach(button => button.disabled = false);
    }
}

function validCredentials() {
    return emailInput.reportValidity() && passwordInput.reportValidity();
}

async function signup() {
    if (!fullNameInput.value.trim()) {
        fullNameInput.setCustomValidity('Vui lòng nhập họ tên');
        fullNameInput.reportValidity();
        fullNameInput.setCustomValidity('');
        return;
    }
    if (!validCredentials()) return;
    await sendRequest('/auth/signup', {
        method: 'POST',
        body: JSON.stringify({
            fullName: fullNameInput.value.trim(),
            email: emailInput.value.trim(),
            password: passwordInput.value
        })
    });
}

async function login() {
    if (!validCredentials()) return;
    const result = await sendRequest('/auth/login', {
        method: 'POST',
        body: JSON.stringify({
            email: emailInput.value.trim(),
            password: passwordInput.value
        })
    });
    if (result?.response.ok && result.data?.token) {
        localStorage.setItem('jwt', result.data.token);
    }
}

document.querySelector('#demo-signup').addEventListener('click', signup);
document.querySelector('#demo-login').addEventListener('click', login);
document.querySelector('#demo-me').addEventListener('click', () => sendRequest('/users/me'));
document.querySelector('#demo-users').addEventListener('click', () => sendRequest('/users'));
document.querySelector('#demo-logout').addEventListener('click', () => {
    localStorage.removeItem('jwt');
    showResult(null, { message: 'Đã đăng xuất và xóa JWT khỏi trình duyệt' });
});
document.querySelector('#demo-form').addEventListener('submit', event => {
    event.preventDefault();
    login();
});
