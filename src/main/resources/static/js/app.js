function token() {
    return localStorage.getItem('jwt');
}

function api(url, options = {}) {
    const headers = new Headers(options.headers || {});
    headers.set('Content-Type', 'application/json');
    if (token()) headers.set('Authorization', `Bearer ${token()}`);
    return fetch(url, { ...options, headers });
}
