importScripts(
    "https://www.gstatic.com/firebasejs/10.8.0/firebase-app-compat.js"
);
importScripts(
    "https://www.gstatic.com/firebasejs/10.8.0/firebase-messaging-compat.js"
);

self.addEventListener("install", function (e) {
    console.log("fcm service worker가 설치되었습니다.");
    self.skipWaiting();
});

self.addEventListener("activate", function (e) {
    console.log("fcm service worker가 실행되었습니다.");
});

const firebaseConfig = {
    apiKey: "AIzaSyBjk9UV_sprbWteXfmd8Blln9e7hP-1PdM",
    authDomain: "project-dose-f2bab.firebaseapp.com",
    projectId: "project-dose-f2bab",
    storageBucket: "project-dose-f2bab.firebasestorage.app",
    messagingSenderId: "556048234054",
    appId: "1:556048234054:web:d6a99a1703af5603403c47",
    measurementId: "G-GKS22Y7W2Z"
};

// 파이어베이스 초기화
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
    const notificationTitle = payload.title;
    const notificationOptions = {
        body: payload.body
    };
    self.registration.showNotification(notificationTitle, notificationOptions);
});