(function () {
    const toggleBtn = document.getElementById('themeToggle');
    const body = document.body;

    function apply(theme) {
        if (theme === 'dark') {
            body.setAttribute('data-theme', 'dark');
            if (toggleBtn) toggleBtn.textContent = '☀️';
        } else {
            body.removeAttribute('data-theme');
            if (toggleBtn) toggleBtn.textContent = '🌙';
        }
    }

    apply(localStorage.getItem('theme') || 'light');

    if (toggleBtn) {
        toggleBtn.addEventListener('click', () => {
            const next = body.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
            localStorage.setItem('theme', next);
            apply(next);
        });
    }
})();
