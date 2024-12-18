import { initializeApp } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-app.js";
import { getMessaging, getToken, onMessage } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-messaging.js";

const firebaseConfig = {
    apiKey: "AIzaSyARpWti2f7oLQsohw34V-sz5GP2ifYs8Tc",
    authDomain: "project-dose-a471b.firebaseapp.com",
    projectId: "project-dose-a471b",
    storageBucket: "project-dose-a471b.firebasestorage.app",
    messagingSenderId: "827354406670",
    appId: "1:827354406670:web:1dbf7964ba4b568025815f",
    measurementId: "G-9P52WWTFP8"
};

// 파이어베이스 초기화
const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

// 서비스 워커 등록

// let currentToken = null;
window.currentToken = null;


if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/static/firebase-messaging-sw.js')
        .then((registration) => {
            console.log('서비스 워커 등록 완료:', registration);

            // FCM 토큰 발급
            return getToken(messaging, {
                vapidKey: "BDLGtSB0cQ5iQD_VAOpKMxaWwOo_FUoJcAlFlPrF8WWhvU8AgDFnmqQebPeXYhFsuYZzsHZ6FeF9vmatY0lgv3w",
                serviceWorkerRegistration: registration
            });
        })
        .then((token) => {
            currentToken = token;  // 토큰 저장
            if (currentToken) {
                console.log("FCM 토큰:", currentToken);
            } else {
                console.warn("FCM 토큰을 받지 못했습니다.");
            }
        })
        .catch((err) => {
            console.error("서비스 워커 등록 오류 또는 FCM 초기화 오류:", err);
        });
}

// 앱이 열려 있을 때 푸시 알림 수신 처리
onMessage(messaging, (payload) => {
    console.log("포그라운드 알림 수신:", payload);

    const { title, body } = payload.notification;
    new Notification(title, {
        body: body,
        icon: "/icon.png" // 알림 아이콘 경로
    });
});