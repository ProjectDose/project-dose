import { initializeApp } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-app.js";
import { getMessaging } from "https://www.gstatic.com/firebasejs/10.8.0/firebase-messaging.js";

const firebaseConfig = {
    apiKey: "AIzaSyBjk9UV_sprbWteXfmd8Blln9e7hP-1PdM",
    authDomain: "project-dose-f2bab.firebaseapp.com",
    projectId: "project-dose-f2bab",
    storageBucket: "project-dose-f2bab.firebasestorage.app",
    messagingSenderId: "556048234054",
    appId: "1:556048234054:web:d6a99a1703af5603403c47",
    measurementId: "G-GKS22Y7W2Z"
};

const app = initializeApp(firebaseConfig);

const messaging = getMessaging(app);

const vapidKey = "BEtj-LbGtc7qAkD1PxrvS5KSPpOsmMJevik4dNj0Vmwl9cpKL5z-nW8TJPj_HvTdMZ5FjUHgwPQyp_C3mIYn-e4";
export { messaging, vapidKey };