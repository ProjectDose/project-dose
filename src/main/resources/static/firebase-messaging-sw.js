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
    apiKey: "AIzaSyARpWti2f7oLQsohw34V-sz5GP2ifYs8Tc",
    authDomain: "project-dose-a471b.firebaseapp.com",
    projectId: "project-dose-a471b",
    storageBucket: "project-dose-a471b.firebasestorage.app",
    messagingSenderId: "827354406670",
    appId: "1:827354406670:web:1dbf7964ba4b568025815f",
    measurementId: "G-9P52WWTFP8"
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
