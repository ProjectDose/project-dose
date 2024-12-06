const calendar = document.getElementById('calendar');
const calendarHeader = document.getElementById('calendar-header');

// 오늘 날짜 가져오기
const today = new Date();
let currentDate = new Date(today);

// 날짜 포맷 함수 (dd만 반환)
function formatDay(date) {
    return String(date.getDate()).padStart(2, '0');
}

// 월 포맷 함수
function formatMonth(date) {
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${year}-${month}`;
}

// 날짜 박스 생성 함수
function createDayElement(date, showMonth = false) {
    const dayElement = document.createElement('div');
    dayElement.className = 'day';
    dayElement.textContent = formatDay(date);
    dayElement.setAttribute('data-date', date.toISOString());

    if (showMonth) {
        const monthElement = document.createElement('div');
        monthElement.className = 'month-indicator';
        monthElement.textContent = formatMonth(date);
        dayElement.insertBefore(monthElement, dayElement.firstChild);
    }
    return dayElement;
}

// 초기화 함수
function initializeCalendar() {
    calendarHeader.textContent = `오늘: ${formatMonth(today)}`;
    let previousMonth = null;

    for (let i = -3; i <= 3; i++) {
        const date = new Date(today);
        date.setDate(today.getDate() + i);

        const isFirstDayOfMonth = date.getDate() === 1;
        const nextDate = new Date(date);
        nextDate.setDate(date.getDate() + 1);
        const isLastDayOfMonth = nextDate.getDate() === 1;

        const showMonth = isFirstDayOfMonth || isLastDayOfMonth;
        calendar.appendChild(createDayElement(date, showMonth));
        previousMonth = date.getMonth();
    }
}

function handleScroll() {
    const scrollLeft = calendar.scrollLeft;
    const scrollWidth = calendar.scrollWidth;
    const clientWidth = calendar.clientWidth;

    if (scrollLeft < 50) {
        for (let i = 0; i < 3; i++) {
            const firstChild = calendar.firstChild;
            const firstDate = new Date(firstChild.getAttribute('data-date'));
            firstDate.setDate(firstDate.getDate() - 1);

            const isFirstDayOfMonth = firstDate.getDate() === 1;
            const nextDate = new Date(firstDate);
            nextDate.setDate(firstDate.getDate() + 1);
            const isLastDayOfMonth = nextDate.getDate() === 1;

            const showMonth = isFirstDayOfMonth || isLastDayOfMonth;
            calendar.insertBefore(createDayElement(firstDate, showMonth), firstChild);
        }
        calendar.scrollLeft += 240; // 새로 추가된 날짜만큼 스크롤 위치 조정
    }

    if (scrollLeft + clientWidth > scrollWidth - 50) {
        for (let i = 0; i < 3; i++) {
            const lastChild = calendar.lastChild;
            const lastDate = new Date(lastChild.getAttribute('data-date'));
            lastDate.setDate(lastDate.getDate() + 1);

            const isFirstDayOfMonth = lastDate.getDate() === 1;
            const nextDate = new Date(lastDate);
            nextDate.setDate(lastDate.getDate() + 1);
            const isLastDayOfMonth = nextDate.getDate() === 1;

            const showMonth = isFirstDayOfMonth || isLastDayOfMonth;
            calendar.appendChild(createDayElement(lastDate, showMonth));
        }
    }
}
calendar.addEventListener('click', (event) => {
    const target = event.target;

    // 클릭한 요소가 .day 클래스인지 확인
    if (target.classList.contains('day')) {
        // 이전에 선택된 날짜에서 'selected' 클래스 제거
        const previouslySelected = document.querySelector('.day.selected');
        if (previouslySelected) {
            previouslySelected.classList.remove('selected');
        }

        // 현재 클릭된 날짜에 'selected' 클래스 추가
        target.classList.add('selected');
    }
});
// 이벤트 연결
initializeCalendar();
calendar.addEventListener('scroll', handleScroll);