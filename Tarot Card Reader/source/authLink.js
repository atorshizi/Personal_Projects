import { getAuth, signOut } from 'https://www.gstatic.com/firebasejs/10.12.0/firebase-auth.js';
import firebaseApp from './firebaseInit.js';

// Access initialized Firebase App
const app = firebaseApp;

const authLink = document.getElementById('authlink');

/**
 * EventListener for the Login/SignOut button. Redirects to the login page if the user is logged out
 *  and signs the user out if they are already logged in.
 */
authLink.addEventListener('click', function(event) {
    event.preventDefault();

    const auth = getAuth(app);
    auth.onAuthStateChanged(user => {
        if(user) {
            signOut(auth)
                .then(() => {
                    alert('Successfully signed out!');
                    window.location.reload(); //eslint-disable-line
                })
                .catch((error) => {
                    const errorMessage = error.message;
                    alert(errorMessage);
                    console.log(error.code, error.message);
                });
        } else {
            window.location.href = 'login.html'; //eslint-disable-line
        }
    });
});
