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
//오늘날짜 자동으로 받아오기
document.addEventListener('DOMContentLoaded', () => {
    const calendarHeader = document.getElementById('calendar-header');
    const formattedDate = today.toISOString().split('T')[0];
    calendarHeader.textContent = `오늘 : ${formattedDate}`
})
// 날짜 박스 생성 함수
function createDayElement(date, showMonth = false) {
    const dayElement = document.createElement('div');
    dayElement.className = 'day';
    dayElement.textContent = formatDay(date);
    // 로컬 시간대를 기준으로 날짜를 설정
    const localDateString = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
    dayElement.setAttribute('data-date', localDateString);

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

    if (target.classList.contains('day')) {
        const previouslySelected = document.querySelector('.day.selected');
        if (previouslySelected) {
            previouslySelected.classList.remove('selected');
        }

        target.classList.add('selected');

        // 선택된 날짜 가져오기
        const clickedDate = target.getAttribute('data-date');
        if (!clickedDate) return;

        // 헤더 업데이트 (로컬 시간대 기준으로 날짜 표시)
        calendarHeader.textContent = `선택된 날짜: ${clickedDate}`;

        // 서버에서 해당 날짜의 데이터 가져오기
        fetch(`/api/Schedules/${clickedDate}`)
            .then((response) => {
                if (!response.ok) throw new Error("데이터 요청 실패");
                return response.json();
            })
            .then((data) => {
                console.log(data); // 서버에서 받은 데이터를 콘솔에 출력하여 확인
                updateScheduleTable(data);
            })
            .catch((error) => console.error("Error fetching schedule:", error));
    }
});

// 테이블 업데이트 함수
function updateScheduleTable(data) {
    const scheduleContainer = document.querySelector(".dose-schedule-container table");
    scheduleContainer.innerHTML = `
        <tr>
            <th>시간</th>
            <th>약물명</th>
            <th>용량</th>
            <th>요일</th>
            <th>수정/삭제</th>
        </tr>
    `;

    if (!data || data.length === 0) {
        const noDataRow = document.createElement("tr");
        noDataRow.innerHTML = `<td colspan="5">해당 날짜에 저장된 스케줄이 없습니다.</td>`;
        scheduleContainer.appendChild(noDataRow);
        return;
    }

    data.forEach((item) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td class = "doseTime">${item.doseTime}</td>
            <td class = "medicationName">${item.medicationName}</td>
            <td class = "dosage">${item.dosage}</td>
            <td>${item.daysOfWeek}</td>
            <td>
                <button class="edit-btn">수정</button>
                <button class="delete-btn">삭제</button>
            </td>
        `;
        scheduleContainer.appendChild(row);
        //삭제 로직
        row.querySelector('.delete-btn').addEventListener('click', () => {
            fetch(`/api/doseschedule/delete/${item.scheduleId}`, {
                method: 'DELETE',
            })
                .then(response => {
                    if (response.ok) {
                        console.log("삭제 완료");
                        row.remove();
                        location.reload()
                    } else {
                        console.error("삭제 오류");
                    }
                })
                .catch(error => console.error("삭제 오류", error));
            console.log("삭제 버튼 클릭", item);
        });

        //수정로직
        row.querySelector(`.edit-btn`).addEventListener('click', () => {
            const modal = document.getElementById('edit-modal');
            const saveBtn = document.getElementById('save-btn');
            const cancelBtn = document.getElementById('cancel-btn');

            document.getElementById('doseTime').value = item.doseTime;
            document.getElementById('medicationName').value = item.medicationName;
            document.getElementById('dosage').value = item.dosage;
            modal.style.display = 'block';

            saveBtn.addEventListener('click',()=>{
                const updatedDoseTime = document.getElementById('doseTime').value;
                const updatedMedicationName = document.getElementById('medicationName').value;
                const updatedDosage = document.getElementById('dosage').value;

                const updatedData = {
                    doseTime: updatedDoseTime || item.doseTime,
                    medicationName: updatedMedicationName || item.medicationName,
                    dosage: updatedDosage || item.dosage
                };

                fetch(`/api/doseschedule/update/${item.scheduleId}`, {
                    method: 'PuT',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(updatedData)
                })
                .then((response) => {
                    if(response.ok) {
                        row.querySelector('.doseTime').textContent = updatedData.doseTime;
                        row.querySelector('.medicationName').textContent = updatedData.medicationName;
                        row.querySelector('.dosage').textContent = updatedData.dosage;
                        modal.style.display = 'none';
                    }else{
                        console.error("수정 오류");
                    }
                })
                    .catch(error => console.error("수정 오류",error));
            });
            cancelBtn.addEventListener('click',()=>{
                modal.style.display = 'none';
            });
        });
    });
}
//새로운 데이터 입력 페이지로 이동.
document.addEventListener('DOMContentLoaded', function () {
    const addScheduleButton = document.getElementById('add-schedule-button');

    addScheduleButton.addEventListener('click', function () {
        // 버튼 클릭 시 newdoseschedule.html 페이지로 이동
        window.location.href = '/NewDoseSchedule';
    });
});

// 초기화 호출
initializeCalendar();
calendar.addEventListener('scroll', handleScroll);