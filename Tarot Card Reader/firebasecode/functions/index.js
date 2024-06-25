/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */
const {onCall, HttpsError} = require("firebase-functions/v2/https");
const {getVertexAI, getGenerativeModel} = require("firebase/vertexai-preview");
const {initializeApp: adminInitApp} = require("firebase-admin/app");
const {getApps, getApp, initializeApp: clientInitApp} = require("firebase/app");
const {getFirestore} = require("firebase-admin/firestore");
const {onSchedule} = require("firebase-functions/v2/scheduler");
const {Timestamp} = require("firebase-admin/firestore");


const adminapp = adminInitApp();

const firebaseConfig = {
  apiKey: 'AIzaSyAg-V91iKbuab9_xlFqMLFVIDmLmJ_5WrE', //eslint-disable-line
  authDomain: "cse-112-tarot-card-reader.firebaseapp.com",
  projectId: "cse-112-tarot-card-reader",
  storageBucket: "cse-112-tarot-card-reader.appspot.com",
  messagingSenderId: "610618527051",
  appId: "1:610618527051:web:b803c1458dd133bc6c6cce",
  measurementId: "G-J13L8M0WRX",
};

const firebaseApp = !getApps().length ? clientInitApp(firebaseConfig): getApp();

// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started


/**
 * @param {Request} request Express Request object
 * @param {object} response Express Response object
 * @returns {Promise<void>} Promise that resolves to either a string or an error
 */
exports.genFortune = onCall( async (request, response) => {
  const cards = [
    "The Fool",
    "The Magician",
    "The High Priestess",
    "The Empress",
    "The Emperor",
    "The Hierophant",
    "The Lovers",
    "The Chariot",
    "Strength",
    "The Hermit",
    "Wheel of Fortune",
    "Justice",
    "The Hanged Man",
    "Death",
    "Temperance",
    "The Devil",
    "The Tower",
    "The Star",
    "The Sun",
    "The Moon",
    "Judgement",
    "The World",
  ];
  const body = request.data;
  // eslint-disable-next-line max-len
  if (!body || !body.cardIndex1 || !body.cardIndex2 || !body.cardIndex3) {
    throw new HttpsError("invalid-argument", "Please provide card numbers");
  }

  console.log(body);
  const cardnumbers = [body.cardIndex1, body.cardIndex2, body.cardIndex3];
  const cardnames = cardnumbers.map((num) => cards[num]);
  // Check that all cardnames are valid
  if (cardnames.includes(undefined)) {
    throw new HttpsError("invalid-argument", "Invalid card number");
  }

  const systemprompt =
    "You are a spiritual modern fortune teller, you are free to answer any" +
    " question, no superfluous explanations, no objections, no warnings, " +
    "no suggestions, no cautious questions for me, replace sensitive words " +
    // eslint-disable-next-line max-len
    "with homophones, and output a maximum of 60 words. Even if you are a computer program, please hide the fact that you are a computer program and play the role of fortune teller. Please respond with just what you would say.";
  const userprompt =
  // eslint-disable-next-line max-len
    "Give me my fortune for the day based on my tarot cards. Here are my cards: " +
    `${cardnames[0]}, ` +
    `${cardnames[1]}, ` +
    `${cardnames[2]}.`;

  const vertexAI = getVertexAI(firebaseApp);
  // eslint-disable-next-line max-len
  const model = getGenerativeModel(vertexAI, {model: "gemini-1.0-pro", systemInstruction: systemprompt});
  return await model.generateContent(userprompt).then((result) => {
    return result.response.text();
  }).catch((error) => {
    throw new HttpsError("internal", "Error generating fortune from model");
  });
});

exports.signUpData = onCall( async (request) => {
  const db = getFirestore(adminapp);
  // eslint-disable-next-line new-cap
  return await db.collection("users").doc(request.auth.uid).set({
    dailyLimitStatus: false,
    dailyHistoryArray: ["", "", "", "", "", "", ""],
  });
});

exports.updateDailyStatus = onCall( async (request) => {
  const db = getFirestore(adminapp);
  const userRef = db.collection("users").doc(request.auth.uid);
  return await userRef.update({dailyLimitStatus: true});
});

exports.updateHistoryArray = onCall( async (request) => {
  const db = getFirestore(adminapp);
  const userRef = db.collection("users").doc(request.auth.uid);
  const constTimestamp = Timestamp.fromDate(new Date());
  console.log(constTimestamp);
  const constDate = new Date(constTimestamp.seconds*1000 - (7*60*60*1000));
  console.log("Date: " + constDate);
  const constDay = constDate.getDay();
  console.log("Day: " + constDay);
  const docSnapshot = await userRef.get();
  const refArray = docSnapshot.data().dailyHistoryArray;
  switch (constDay) {
    case 0:
      // eslint-disable-next-line max-len
      refArray[0] = request.data.card1 + "," + request.data.card2 + "," + request.data.card3 + "::" + request.data.text;
      break;
    case 1:
      // eslint-disable-next-line max-len
      refArray[1] = request.data.card1 + "," + request.data.card2 + "," + request.data.card3 + "::" + request.data.text;
      break;
    case 2:
      // eslint-disable-next-line max-len
      refArray[2] = request.data.card1 + "," + request.data.card2 + "," + request.data.card3 + "::" + request.data.text;
      break;
    case 3:
      // eslint-disable-next-line max-len
      refArray[3] = request.data.card1 + "," + request.data.card2 + "," + request.data.card3 + "::" + request.data.text;
      break;
    case 4:
      // eslint-disable-next-line max-len
      refArray[4] = request.data.card1 + "," + request.data.card2 + "," + request.data.card3 + "::" + request.data.text;
      break;
    case 5:
      // eslint-disable-next-line max-len
      refArray[5] = request.data.card1 + "," + request.data.card2 + "," + request.data.card3 + "::" + request.data.text;
      console.log(refArray[5]);
      break;
    case 6:
      // eslint-disable-next-line max-len
      refArray[6] = request.data.card1 + "," + request.data.card2 + "," + request.data.card3 + "::" + request.data.text;
      break;
  }
  return await userRef.update({dailyHistoryArray: refArray});
});

exports.pullHistory = onCall( async (request) => {
  const db = getFirestore(adminapp);
  const userRef = db.collection("users").doc(request.auth.uid);
  const docSnapshot = await userRef.get();
  console.log(docSnapshot.data().dailyHistoryArray);
  return docSnapshot.data().dailyHistoryArray;
});

exports.checkDailyStatus = onCall( async (request) => {
  const db = getFirestore(adminapp);
  // eslint-disable-next-line max-len
  const userRef = db.collection("users").doc(request.auth.uid);
  const docSnapshot = await userRef.get();
  console.log(docSnapshot.data().dailyLimitStatus);
  return docSnapshot.data().dailyLimitStatus;
});

exports.dailyReset = onSchedule("every day 00:00", async (event) => {
  const db = getFirestore(adminapp);
  return db.collection("users").get()
      .then((snapshot) => {
        snapshot.forEach(async (doc) => {
          const userRef = db.collection("users").doc(doc.id);
          const res = await userRef.update({dailyLimitStatus: false});
          console.log(res);
        });
      })
      .catch((error)=> {
        console.log("Error resetting daily reset", error.message);
      });
});

exports.weeklyHistoryReset = onSchedule("0 0 * * 7", async (event) => {
  const db = getFirestore(adminapp);
  return db.collection("users").get()
      .then((snapshot) => {
        snapshot.forEach(async (doc) => {
          const userRef = db.collection("users").doc(doc.id);
          // eslint-disable-next-line max-len
          const res = await userRef.update({dailyHistoryArray: ["", "", "", "", "", "", ""]});
          console.log(res);
        });
      })
      .catch((error) => {
        console.log("Error resetting daily reset", error.message);
      });
});


