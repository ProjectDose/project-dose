import { getToken } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-messaging.js";
import { messaging, vapidKey } from "../firebase/firebase-config.js";

// 페이지 로드 시 실행될 초기화 함수
export async function initializeServiceWorker() {
    if ('serviceWorker' in navigator) {
        try {
            const existingRegistration = await navigator.serviceWorker.getRegistration('./firebase-messaging-sw.js');

            if (existingRegistration) {
                console.log('기존 서비스 워커가 이미 등록되어 있습니다:', existingRegistration.scope);
            } else {
                console.log('서비스 워커가 등록되지 않았으므로 새로 등록합니다.');
                const registration = await navigator.serviceWorker.register('./firebase-messaging-sw.js');
                console.log('새 서비스 워커가 등록되었습니다:', registration.scope);

                // 알림 권한 요청
                await requestNotificationPermission();
            }
        } catch (error) {
            console.error('서비스 워커 등록 또는 확인 중 오류 발생:', error);
        }
    } else {
        console.error('서비스 워커가 브라우저에서 지원되지 않습니다.');
    }
}

// 알림 권한 요청 함수
async function requestNotificationPermission() {
    try {
        const permission = await Notification.requestPermission();
        if (permission === 'granted') {
            console.log('알림 권한이 부여되었습니다.');
        } else {
            console.log('알림 권한이 거부되었습니다.');
        }
    } catch (error) {
        console.error('알림 권한 요청 중 오류 발생:', error);
    }
}

// 버튼 클릭 시 FCM 토큰 요청
export async function requestFcmToken(retryCount = 0) {
    if ('serviceWorker' in navigator) {
        try {
            const registration = await navigator.serviceWorker.ready;
            console.log('서비스 워커가 준비되었습니다:', registration.scope);

            const currentToken = await getToken(messaging, { vapidKey });

            if (currentToken) {
                console.log('FCM Token:', currentToken);
            } else {
                console.warn('FCM 토큰이 없습니다. 권한을 확인하세요.');
            }
        } catch (err) {
            console.error('푸시 토큰 가져오는 중 오류 발생:', err);

            if (retryCount < 3) {
                console.log(`재시도 중... (${retryCount + 1}/3)`);
                setTimeout(() => requestFcmToken(retryCount + 1), 1000);
            } else {
                console.error('푸시 토큰 요청이 실패했습니다. 재시도를 중단합니다.');
            }
        }
    }
}
