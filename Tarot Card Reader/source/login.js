// Import the functions you need from the SDKs you need
import { getAuth, signInWithEmailAndPassword } from 'https://www.gstatic.com/firebasejs/10.12.0/firebase-auth.js';
import firebaseApp from './firebaseInit.js';
// For Firebase JS SDK v7.20.0 and later, measurementId is optional

const app = firebaseApp;
//sign-in button
const login = document.getElementById('login-button');
login.addEventListener('click', function(event){
  event.preventDefault();

  //inputs
  const email = document.getElementById('login').value;
  const password = document.getElementById('password').value;

  const auth = getAuth(app);
  signInWithEmailAndPassword(auth, email, password)
  .then((userCredential) => {
    // Signed in 
    // const user = userCredential.user;
    console.log('User created:', userCredential.user);
    //alert('logged in successfully');
    window.location.href = 'index.html'; //eslint-disable-line
    // ...
  })
  .catch((error) => {
    const errorMessage = error.message;
    alert(errorMessage);
    console.log(error.code, error.message);
  });

});