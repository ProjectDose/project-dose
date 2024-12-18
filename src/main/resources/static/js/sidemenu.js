document.addEventListener('DOMContentLoaded', () => {
    const menuButton = document.getElementById('menuButton');
    const sideMenu = document.getElementById('sideMenu');
    const closeButton = document.getElementById('closeButton');

    if (menuButton && sideMenu && closeButton) {
        menuButton.addEventListener('click', () => {
            sideMenu.classList.add('open');
        });

        closeButton.addEventListener('click', () => {
            sideMenu.classList.remove('open');
        });

        document.addEventListener('click', (event) => {
            if (!sideMenu.contains(event.target) && !menuButton.contains(event.target)) {
                sideMenu.classList.remove('open');
            }
        });
    }
});
