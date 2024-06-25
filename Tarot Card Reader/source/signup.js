// Import the functions you need from the SDKs you need
import { getAuth, createUserWithEmailAndPassword } from 'https://www.gstatic.com/firebasejs/10.12.0/firebase-auth.js';
import firebaseApp, {signUpData} from './firebaseInit.js';
// https://firebase.google.com/docs/web/setup#available-libraries

const app = firebaseApp;
//register button
const register = document.getElementById('signup-button');
register.addEventListener('click', function (event) {
  event.preventDefault();

  //inputs
  const email = document.getElementById('login').value;
  const password = document.getElementById('password').value;

  const auth = getAuth(app);
  createUserWithEmailAndPassword(auth, email, password)
    .then((userCredential) => {
      // Signed up 
      // const user = userCredential.user;
      console.log('User created:', userCredential.user);
      alert('Created Account!');
      signUpData().then(
          async() => {
              console.log('Successful db write');
              window.location.href = 'index.html'; //eslint-disable-line
          }
      ).catch( async(error) => {
          console.log('db write failed', error.message);
          window.location.href = 'index.html'; //eslint-disable-line
      });
    })
    .catch((error) => {
      const errorMessage = error.message;
      alert(errorMessage);
      console.log(error.code, error.message);
      // ..
    });
});