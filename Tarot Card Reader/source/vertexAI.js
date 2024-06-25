
import {drawCards, generateAIHandler} from './scripts.js';
import {model, updateDailyStatus, updateHistoryArray} from './firebaseInit.js';
// eslint-disable-next-line no-unused-vars

import {cards} from './scripts.js';
import {genFortune} from './firebaseInit.js';


const aiModel = model;

const genButton = document.getElementById('daily-generate-btn');
const cardElements = document.querySelectorAll('.cardflip');
const loadingAnimation = document.getElementById('loading-animation');

function shuffleCards() {
    cardElements.forEach((card, index) => {
        card.classList.remove('card-1', 'card-2', 'card-3');
        card.classList.add(`card-${index + 1}`);
    });
}

function stopShuffle() {
    cardElements.forEach(card => {
        card.classList.remove('card-1', 'card-2', 'card-3');
    });
}

/**
 * Provides and AI-generated fortune for the day based on three randomly selected tarot cards.
 * @param card1 First randomly generated card
 * @param card2 Second randomly generated card
 * @param card3 Third randomly generated card
 * @returns {Promise<*>} String that contains AI-generated daily fortune
 */
async function generateDailyFortune(card1, card2, card3) {
    const prompt = `Create a fortune for the day based on these three tarot cards: ${card1}, ${card2}, ${card3}. Answer in 50 to 60 words.`;
    //Serialize the three cards based on their index in the cards array
    const cardindex1 = cards.indexOf(card1);
    console.log(cardindex1);
    const cardindex2 = cards.indexOf(card2);
    const cardindex3 = cards.indexOf(card3);

    //Check if the cards are valid
    if (cardindex1 === -1 || cardindex2 === -1 || cardindex3 === -1) {
        console.log('Invalid card');
        return;
    }

    //Call the cloud function to generate the fortune
    return genFortune({ cardIndex1: cardindex1, cardIndex2: cardindex2, cardIndex3: cardindex3 }).then(
        async (result) => {
            const text = await result.data;
            console.log(result);
            return text;
        }
    ).catch(async (error) => {
        console.log('Fortune generation failed, attempting direct ai response: ', error);
        const result = await aiModel.generateContent(prompt);

        const response = result.response;
        const text = response.text();
        console.log(text);
        return text;
    });

}

/**
 * Event Listener for 'daily-generate-btn'. Populates the daily login section with required content
 */
genButton.addEventListener('click', function(event)  {
    event.preventDefault();
    loadingAnimation.classList.remove('hide'); // Show loading animation
    shuffleCards(); // Start the shuffle animation

    let drawnCards = drawCards();
    generateDailyFortune(drawnCards[0], drawnCards[1], drawnCards[2])
        .then((text) => {
            updateDailyStatus().then(async() => {
                console.log('Successful DB update');
                updateHistoryArray({card1: drawnCards[0], card2: drawnCards[1], card3: drawnCards[2], text:text});
            }).catch(async(error) => {
                console.log('db update failed', error.message);
            });
            generateAIHandler(text,drawnCards);
        })
        .catch((error) => {
            console.log(error.code, error.message);
            console.log('Failure');
        })
        .finally(() => {
            loadingAnimation.classList.add('hide'); // Hide loading animation after completion or failure
            stopShuffle();
        });
});

