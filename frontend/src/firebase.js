import firebase from 'firebase/compat/app';
import 'firebase/compat/messaging';

const firebaseConfig = {
  apiKey: process.env.VUE_APP_FIREBASE_API_URL,
  authDomain: "herewhere-b85b9.firebaseapp.com",
  projectId: "herewhere-b85b9",
  storageBucket: "herewhere-b85b9.appspot.com",
  messagingSenderId: "514996625095",
  appId: "1:514996625095:web:3d5f4a2cb206ab62a2a8e2",
  measurementId: "G-KW514RWEN6"
};

firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();
export { messaging };