document.addEventListener('DOMContentLoaded', function () {
    let doseTimeIndex = 2; // 투약 시간 필드 고유 키 시작값
    let daysOfWeekIndex = 2; // 요일 필드 고유 키 시작값

    // 투약 시간 추가 버튼
    document.getElementById('addDoseTimeButton').addEventListener('click', function () {
        const doseTimeFields = document.getElementById('doseTimeFields');

        // 새로운 투약 시간 입력 필드 추가
        const newField = document.createElement('div');
        newField.classList.add('doseTimeField');
        newField.innerHTML = `
            <label for="doseTime${doseTimeIndex}">투약 시간 ${doseTimeIndex}:</label>
            <input type="time" id="doseTime${doseTimeIndex}" name="doseTime[time${doseTimeIndex}]" required>
            <button type="button" class="removeDoseTimeButton">삭제</button>
        `;
        doseTimeFields.appendChild(newField);
        doseTimeIndex++;

        // 삭제 버튼 이벤트 리스너 추가
        newField.querySelector('.removeDoseTimeButton').addEventListener('click', function () {
            doseTimeFields.removeChild(newField);
        });
    });

    // 요일 추가 버튼
    document.getElementById('addDaysOfWeekButton').addEventListener('click', function () {
        const daysOfWeekFields = document.getElementById('daysOfWeekFields');

        // 새로운 요일 입력 필드 추가
        const newField = document.createElement('div');
        newField.classList.add('daysOfWeekField');
        newField.innerHTML = `
            <label for="daysOfWeek${daysOfWeekIndex}">요일 ${daysOfWeekIndex}:</label>
            <select id="daysOfWeek${daysOfWeekIndex}" name="daysOfWeek[day${daysOfWeekIndex}]" required>
                <option value="MONDAY">월요일</option>
                <option value="TUESDAY">화요일</option>
                <option value="WEDNESDAY">수요일</option>
                <option value="THURSDAY">목요일</option>
                <option value="FRIDAY">금요일</option>
                <option value="SATURDAY">토요일</option>
                <option value="SUNDAY">일요일</option>
            </select>
            <button type="button" class="removeDaysOfWeekButton">삭제</button>
        `;
        daysOfWeekFields.appendChild(newField);
        daysOfWeekIndex++;

        // 삭제 버튼 이벤트 리스너 추가
        newField.querySelector('.removeDaysOfWeekButton').addEventListener('click', function () {
            daysOfWeekFields.removeChild(newField);
        });
    });

    // 폼 전송 처리
    document.getElementById('dataForm').addEventListener('submit', function (event) {
        event.preventDefault(); // 폼 제출의 기본 동작을 막음

        // 폼 데이터 수집
        const formData = new FormData(this);

        // 데이터 객체로 변환
        const data = {};
        formData.forEach((value, key) => {
            if (key.startsWith('doseTime[') || key.startsWith('daysOfWeek[')) {
                const [fieldType, fieldKey] = key.match(/(\w+)\[(\w+)\]/).slice(1);
                if (!data[fieldType]) {
                    data[fieldType] = {};
                }
                data[fieldType][fieldKey] = value;
            } else {
                data[key] = value;
            }
        });

        // 기존 코드
        // const data = {};
        // formData.forEach((value, key) => {
        //     if (key.startsWith('doseTime[') || key.startsWith('daysOfWeek[')) {
        //         const fieldType = key.split('[')[0]; // doseTime 또는 daysOfWeek 추출
        //         const fieldKey = key.match(/\[(.+)\]/)[1]; // 고유 키 추출 (time1, day1 등)
        //         if (!data[fieldType]) {
        //             data[fieldType] = {};
        //         }
        //         data[fieldType][fieldKey] = value;
        //     } else {
        //         data[key] = value;
        //     }
        // });

        // 서버로 POST 요청 보내기
        fetch('/api/generate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data), // JSON 형식으로 데이터 보내기
        })
            .then(response => response.json())
            .then(result => {
                console.log('Success:', result);
                alert('데이터가 성공적으로 제출되었습니다.');
                window.location.href = "/";
            })
            .catch(error => {
                console.error('Error:', error);
                alert('데이터 제출에 실패했습니다.');
            });
    });
});
