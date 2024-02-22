importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js');

const firebaseConfig = {
    apiKey: "AIzaSyDj7oqT63Txt19o4BS4D6ao9SGFVYsZabk",
    authDomain: "herewhere-b85b9.firebaseapp.com",
    projectId: "herewhere-b85b9",
    storageBucket: "herewhere-b85b9.appspot.com",
    messagingSenderId: "514996625095",
    appId: "1:514996625095:web:3d5f4a2cb206ab62a2a8e2",
    measurementId: "G-KW514RWEN6",
  };

  firebase.initializeApp(firebaseConfig);
  const messaging = firebase.messaging();