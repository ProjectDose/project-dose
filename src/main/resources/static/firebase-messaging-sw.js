importScripts(
    "https://www.gstatic.com/firebasejs/11.1.0/firebase-app-compat.js"
);
importScripts(
    "https://www.gstatic.com/firebasejs/11.1.0/firebase-messaging-compat.js"
);

self.addEventListener("install", function (e) {
    console.log("fcm service worker가 설치되었습니다.");
    self.skipWaiting();
});

self.addEventListener("activate", function (e) {
    console.log("fcm service worker가 실행되었습니다.");
});

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
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

// 백그라운드 메시지 수신 처리
messaging.onBackgroundMessage((payload) => {
    console.log('[서비스 워커] 백그라운드 메시지 수신:', payload);

    const notificationTitle = payload.notification.title || "알림";
    const notificationOptions = {
        body: payload.notification.body || "새로운 알림이 도착했습니다."
    };

    // 알림 표시
    self.registration.showNotification(notificationTitle, notificationOptions);
});
