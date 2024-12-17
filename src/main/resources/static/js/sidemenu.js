// 메뉴 버튼과 닫기 버튼 가져오기
const menuButton = document.getElementById('menuButton');
const sideMenu = document.getElementById('sideMenu');
const closeButton = document.getElementById('closeButton');

// 메뉴 열기
menuButton.addEventListener('click', () => {
    sideMenu.classList.add('open');
});

// 메뉴 닫기
closeButton.addEventListener('click', () => {
    sideMenu.classList.remove('open');
});

// 메뉴 외부 클릭 시 닫기
document.addEventListener('click', (event) => {
    if (!sideMenu.contains(event.target) && !menuButton.contains(event.target)) {
        sideMenu.classList.remove('open');
    }
});