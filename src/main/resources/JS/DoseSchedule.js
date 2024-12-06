// id가 modify-btn인 엘리먼트 조회
const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
    // 클릭 이벤트가 감지되면 수정 API 요청
    modifyButton.addEventListener('click', event => {
        let params = new URLSearchParams(location.search);
        let id = params.get('id');

        fetch(`/api/DoseSchedule/${id}`, {
            method: 'PUT',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                medicationName: document.getElementById('medicationName').value,
                doseTime: document.getElementById('doseTime').value,
                joinDate: document.getElementById('joinDate').value
            })
        }).then(() => {
            alert('수정이 완료되었습니다');
            location.replace(`/DoseSchedule/${id}`);
        });
    });
}