document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('medicationCalendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        height: 'auto',
        locale: 'ko',
        events: function(fetchInfo, successCallback, failureCallback) {
            const achievementRatesElement = document.getElementById('AchievementRates');
            const achievementRates = JSON.parse(achievementRatesElement.getAttribute('data-achievement-rates') || '{}');

            const events = Object.keys(achievementRates).map(function (date) {
                return {
                    title: achievementRates[date] + '%',
                    start: date,
                    className: getColorClass(achievementRates[date])
                };
            });

            successCallback(events);
        },
        dateClick: function (info) {
            fetch('/report/statistics/daily-details?selectedDate=' + info.dateStr)
                .then(response => {
                    return response.text();
                })
                .then(html => {
                    const detailsContainer = document.getElementById('dailyDetailsContainer');
                    const detailsContent = document.getElementById('dailyDetailsContent');
                    detailsContent.innerHTML = html;
                    detailsContainer.style.display = 'block';
                })
                .catch(error => {
                    console.error('오류 발생:', error);
                });
        }
    });
    calendar.render();

    function getColorClass(achievementRate) {
        if (achievementRate >= 90) return 'achievement-high';
        if (achievementRate >= 70) return 'achievement-medium';
        return 'achievement-low';
    }


    // PDF/이메일 모달 관련 이벤트
    const reportModal = new bootstrap.Modal(document.getElementById('reportModal'));
    const generatePdfBtn = document.getElementById('generatePdfBtn');
    const downloadPdfBtn = document.getElementById('downloadPdfBtn');
    const sendEmailBtn = document.getElementById('sendEmailBtn');


    generatePdfBtn.addEventListener('click', () => {
        reportModal.show();
    });


    downloadPdfBtn.addEventListener('click', () => {
        const startDate = document.getElementById('startDateInput').value;
        const endDate = document.getElementById('endDateInput').value;

        // PDF 다운로드 로직
        window.location.href = `/api/report/pdf?startDate=${startDate}&endDate=${endDate}`;
        reportModal.hide();
    });

    sendEmailBtn.addEventListener('click', () => {
        const startDate = document.getElementById('startDateInput').value;
        const endDate = document.getElementById('endDateInput').value;

        // 이메일 전송 로직
        fetch(`/api/report/email?startDate=${startDate}&endDate=${endDate}`, {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    alert('보고서가 이메일로 전송되었습니다.');
                } else {
                    alert('전송 중 오류가 발생했습니다.');
                }
                reportModal.hide();
            });
    });
});