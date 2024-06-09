importScripts('https://www.gstatic.com/firebasejs/9.6.10/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.6.10/firebase-messaging-compat.js');

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyAhbnSObvhNDXgvkcHCXPgI90-kajOp7sc",
		  authDomain: "flakyfilo-9fd34.firebaseapp.com",
		  projectId: "flakyfilo-9fd34",
		  storageBucket: "flakyfilo-9fd34.appspot.com",
		  messagingSenderId: "499183470309",
		  appId: "1:499183470309:web:b05b771a11f226d453360c",
		  measurementId: "G-DRZDDY096X"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);

// Initialize Firebase Cloud Messaging
const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log('Received background message ', payload);
  // Customize notification here
  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});

