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
    // 현재 날짜 표시
    calendarHeader.textContent = `오늘: ${formatMonth(today)}`;

    let previousMonth = null;

    // 초기 날짜 7일 생성 (3일 과거, 오늘, 3일 미래)
    for (let i = -3; i <= 3; i++) {
        const date = new Date(today);
        date.setDate(today.getDate() + i);
        const isToday = i === 0;
        const currentMonth = date.getMonth();

        const showMonth = previousMonth !== currentMonth;
        calendar.appendChild(createDayElement(date, showMonth));
        previousMonth = currentMonth;
    }
}

// 스크롤 이벤트 핸들러
function handleScroll() {
    const scrollLeft = calendar.scrollLeft;
    const scrollWidth = calendar.scrollWidth;
    const clientWidth = calendar.clientWidth;

    let previousMonth = null;

    // 스크롤이 왼쪽 끝에 가까워지면 과거 날짜 추가
    if (scrollLeft < 50) {
        for (let i = 0; i < 3; i++) {
            const firstDate = new Date(calendar.firstChild.textContent.split('-')[0] || today);
            firstDate.setDate(firstDate.getDate() - 1);
            const currentMonth = firstDate.getMonth();

            const showMonth = previousMonth !== currentMonth;
            calendar.insertBefore(createDayElement(firstDate, showMonth), calendar.firstChild);
            previousMonth = currentMonth;
        }
        calendar.scrollLeft += 240; // 새로 추가된 날짜만큼 스크롤 위치 조정
    }

    // 스크롤이 오른쪽 끝에 가까워지면 미래 날짜 추가
    if (scrollLeft + clientWidth > scrollWidth - 50) {
        for (let i = 0; i < 3; i++) {
            const lastDate = new Date(calendar.lastChild.textContent.split('-')[0] || today);
            lastDate.setDate(lastDate.getDate() + 1);
            const currentMonth = lastDate.getMonth();

            const showMonth = previousMonth !== currentMonth;
            calendar.appendChild(createDayElement(lastDate, showMonth));
            previousMonth = currentMonth;
        }
    }
}

// 이벤트 연결
initializeCalendar();
calendar.addEventListener('scroll', handleScroll);
