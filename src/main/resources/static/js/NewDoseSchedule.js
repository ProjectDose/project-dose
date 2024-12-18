// id가 modify-btn인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/newDoseSchedule/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                scheduleId: document.getElementById('scheduleId').value,
                userId: document.getElementById('userId').value,
                medicationName: document.getElementById('medicationName').value,
                doseTime: document.getElementById('doseTime').value,
                dosage: document.getElementById('dosage').value,
                repeatInterval: document.getElementById('repeatInterval').value,
                daysOfWeek: document.getElementById('daysOfWeek').value,
                startDate: document.getElementById('startDate').value
            })
        }).then(() => {
            alert('수정이 완료되었습니다');
            location.replace(`/Calendar`);
        });
    });
}

// 동적 입력 필드 추가
document.addEventListener('DOMContentLoaded', function() {
    let doseTimeIndex = 2;  // 첫 번째 투약 시간 필드는 이미 존재하므로 2부터 시작
    let daysOfWeekIndex = 2;  // 첫 번째 요일 필드는 이미 존재하므로 2부터 시작

    // 투약 시간 추가 버튼
    document.getElementById('addDoseTimeButton').addEventListener('click', function() {
        const doseTimeFields = document.getElementById('doseTimeFields');

        // 새로운 투약 시간 입력 필드 추가
        const newField = document.createElement('div');
        newField.classList.add('doseTimeField');
        newField.innerHTML = `
            <label for="doseTime${doseTimeIndex}">투약 시간 ${doseTimeIndex}:</label>
            <input type="time" id="doseTime${doseTimeIndex}" name="doseTime[${doseTimeIndex}]" required>
            <button type="button" class="removeDoseTimeButton">삭제</button> <!-- 삭제 버튼 추가 -->
        `;
        doseTimeFields.appendChild(newField);
        doseTimeIndex++;

        // 삭제 버튼 이벤트 리스너 추가
        newField.querySelector('.removeDoseTimeButton').addEventListener('click', function() {
            doseTimeFields.removeChild(newField);
        });
    });

    // 요일 추가 버튼
    document.getElementById('addDaysOfWeekButton').addEventListener('click', function() {
        const daysOfWeekFields = document.getElementById('daysOfWeekFields');

        // 새로운 요일 입력 필드 추가
        const newField = document.createElement('div');
        newField.classList.add('daysOfWeekField');
        newField.innerHTML = `
            <label for="daysOfWeek${daysOfWeekIndex}">요일 ${daysOfWeekIndex}:</label>
            <select id="daysOfWeek${daysOfWeekIndex}" name="daysOfWeek[${daysOfWeekIndex}]" required>
                <option value="MONDAY">월요일</option>
                <option value="TUESDAY">화요일</option>
                <option value="WEDNESDAY">수요일</option>
                <option value="THURSDAY">목요일</option>
                <option value="FRIDAY">금요일</option>
                <option value="SATURDAY">토요일</option>
                <option value="SUNDAY">일요일</option>
            </select>
            <button type="button" class="removeDaysOfWeekButton">삭제</button> <!-- 삭제 버튼 추가 -->
        `;
        daysOfWeekFields.appendChild(newField);
        daysOfWeekIndex++;

        // 삭제 버튼 이벤트 리스너 추가
        newField.querySelector('.removeDaysOfWeekButton').addEventListener('click', function() {
            daysOfWeekFields.removeChild(newField);
        });
    });

    // 폼 전송 처리
    document.getElementById('dataForm').addEventListener('submit', function(event) {
        event.preventDefault();  // 폼 제출의 기본 동작을 막음

        // 폼 데이터 수집
        const formData = new FormData(this);

        // 데이터 객체로 변환
        const data = {};
        formData.forEach((value, key) => {
            // 배열 형태로 데이터 저장
            if (key.startsWith('doseTime') || key.startsWith('daysOfWeek')) {
                const index = key.match(/\[(\d+)\]/)[1];  // [index] 부분을 추출
                if (!data[key.split('[')[0]]) {
                    data[key.split('[')[0]] = {};
                }
                data[key.split('[')[0]][index] = value;
            } else {
                data[key] = value;
            }
        });

        // fetch를 사용하여 서버로 POST 요청 보내기
        fetch('/api/generate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),  // JSON 형식으로 데이터 보내기
        })
            .then(response => response.json())
            .then(result => {
                console.log('Success:', result);
                alert('데이터가 성공적으로 제출되었습니다.');
                window.location.href="/";
            })
            .catch(error => {
                console.error('Error:', error);
                alert('데이터 제출에 실패했습니다.');
            });
    });
});
