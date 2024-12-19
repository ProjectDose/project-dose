import { initializeApp } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-app.js";
import { getMessaging, getToken, onMessage } from "https://www.gstatic.com/firebasejs/11.1.0/firebase-messaging.js";

const firebaseConfig = {
    apiKey: "AIzaSyDUpnur1yyWVo4OUkUajtj4_w81Ob6hobg",
    authDomain: "project-dose-a8c02.firebaseapp.com",
    projectId: "project-dose-a8c02",
    storageBucket: "project-dose-a8c02.firebasestorage.app",
    messagingSenderId: "733238728889",
    appId: "1:733238728889:web:a888e0b9ec2eee3ef7a368",
    measurementId: "G-2ZY91FFX08"
};

// 파이어베이스 초기화
const app = initializeApp(firebaseConfig);
const messaging = getMessaging(app);

// 서비스 워커 등록
window.currentToken = null;

if ('serviceWorker' in navigator) {
    navigator.serviceWorker.register('/static/firebase-messaging-sw.js')
        .then((registration) => {
            console.log('서비스 워커 등록 완료:', registration);

            // FCM 토큰 발급
            return getToken(messaging, {
                vapidKey: "BMc4FNQ5IlbaIjMlBvwR5AHHeX8yAFZXlhzaDUVtwnQloDXpW6XBtwteyT2tZGMGSS4NI082RXtER4qdLXabBx8",
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
