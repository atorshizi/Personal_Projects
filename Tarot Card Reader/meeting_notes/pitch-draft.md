# Starting Pitch Draft

## Problem
### Problem statement: 
People who are struggling with uncertainty in their lives want to turn to a source of guidance. This type of uncertainty can produce stressed, usually because of something in particular going on in their life.

### User Profiles:

Students: 
Generally a younger age group, as they are not working adults they have more time on their hands, therefore on things they look for in entertainment. Once  source of entertainment for them could be fortune telling. 

What are they looking for specifically?
1. Interactions: young people prefer to use fortune telling apps that ask you questions (about your recent situations). requires engagement of some sort. sometimes, even vocal or image interactions. 
2. Unpredictable: The responses are not just yes no or uncertain, they want unique and personalized answers
3. Visually appealing (looking fancy and modern but easy to use)


Old people:
With many things such as death out of their control they may look for certainty and direction in fortunes telling.

What are they looking for specifically?
1. It is easy to use, straightforward visually
2. Care more about other people(predictions for other, like kids)
3. answers are not necessary bold and adventurous, but reassuring.

Adults:
Many adults experience a so called "Midlife Crisis", this causes them to turn somewhere to guidance. Fortune telling can offer than guidance and help them make decisions about their careers, relationships, and other important life choices. Or help them understand their own personalities, relationships, and future.

What are they looking for specifically?
1. No negative predictions
2. encouragement, guidance, meaning. give them hope.

## Hypothetical User profiles:

User #1: Linda

She is a 21-year-old student at university. She is a double-major student studying communication and cs. Most of her time is occupied by lectures and homework, so she doesn’t have an active social life. She is constantly stressed about upcoming exams and essays. Sometimes, she feels like losing control of her own life and her life is full of uncertainty: “Am I going to finish my CS homework on time?” “Am I going to get that summer internship?” “How well should I do on the midterm so that I can get an A in the end?” For Linda, the most peaceful time in her day was the one and half hour before bed. She does not want to go to bed right away after finishing homework, meanwhile, she doesn’t want to waste her time on social media. Therefore, she decided to play some of tarot cards fortune-telling apps in her spare time occasionally. On the one hand, she loves the straightforward interface and the constant interaction in the game, on the other hand, she felt her stress about the future was relied on by playing tarot cards. 


User #2:  John

He is a 37-year-old receptionist working for a mid-size construction company. He works from 9 am-6 pm every day. His job is repetitive and boring. There is no creative element in his works. His salary was a little less than average compared to other 37 years old. He has children to raise and mortgage to pay. Every day on the way back home, he always thinks about the future and reflects on his current life. He feels a deep sense of hollowness and despair because he sees himself doing the same meaningless job for the next 20 years without accomplishing any major achievement in life. Recently, he started to play tarot cards on a fortune-telling app. He soon begins to love this game because he found hope in life by playing tarot cards. He seeks guidance and direction in this game, which gives him meaning to his repetitive job. 



## Appitite
We have a total of **5 Weeks** to complete our project.

Week 1:
UI design

Week 2:
User interface and basic functionality implementation

Week 3:
Testing and polishing code

Week 4:
Adding new features based on time constraints

Week 5:
Finalizing and submitting the project

## Solution
### Statement of Purpose/Goals:
Purpose: To have an outlet that provides people with unique and positive insights into the future while also being a source of entertainment. To accomplish this we will be using tarot cards as it is a common fortune telling method and there are many people who believe in it. It is interactive, has a lot of visual appeal and would work well in an app so this is the type of fortune telling that will help us achieve our purpose the best. To get a wide user base our intention is to design the app for both web and phone users. 

### Features:
Basic: 
1. Three card tarot reading for user input
  * Create: We will need to create a way to store data on the 78 Tarot cards and their meaning
  * Read: We will then read data from the user by having them select some Tarot cards
  * Update: We will use the read information to update the output that we give to the user
  * Delete: We will delete previous input and data to give a fresh fortune everytime
2. Adjustable screen size for desktop or mobile
  * Read: We will need to ensure that users are able to input responses on any device so that we can read their responses
3. Offline support in response generation
  * Update: We will use the read information to update the output that we give to the user

Additional:
1. Personalized respones from Chat GPT API
  * Update: We will use Chat GPT API to generate our output from the data we read from out user input. We will then use the AI generated response to update the user.
2. Link sharing
  * Delete: Before deleting the users fortune, we will give them the option of sharing their response through a link.
3. Voice Interaction
  * Update: When the user is updated with the response we will use voice audio in addition to the written response to present the fortune. 
4. Animation while waiting for response
  * Update: While updating the user an animation will be played to keep the user engaged



### UI/UX:
The general theme for our UI/UX is a mix between a clasical and modern style.

Homescreen:

![Homescreen](https://github.com/cse110-sp21-group02/cse110-sp21-group02/blob/f2e4699ff2d7fa76bc74e76163b48091a1857d5f/specs/pitch/Home%20Screen.png)

Tarot Reader:

![Tarot Reader](https://github.com/cse110-sp21-group02/cse110-sp21-group02/blob/0414f4cbf28c10d0161c11d300e89a3ebb334bcf/specs/pitch/Tarot%20Reader.png)

## Rabbit Holes
1. Data: in order to do truly personalized readings we would need to store and write a large amount of custom fortunes. Workaround: Use an AI generative system, implement as an additional feature if time allows.
2. Design: designing from CSS is difficult to visualize and will delay the team's view of the project. Before any front end coding we will create a Figma and aim to exactly replicate the design on there.
3. Complexity/clutter: lots of fortune telling apps are filled with tons of features that take a lot of time to implement and would distract from the rest of the design. We will focus on using a single standard tarot card deck.
4. Avoid adding too many features at once to the app. Start off with coding the necessary features then add the other additional features once the necessary features are working properly. Follow the workflow timeline specified in the Appitite portion.

## No Gos
1. No external databases.
2. Tarot card reading diversity, there are many different readings that can be performed and we don’t have time for all of them. To avoid getting lost we will focus first on a simple 3 card reading.
3. Changing to a non-standard Tarot card deck.

## Research
- [Types of Readings](https://midtownmanhattanpsychic.com/types-tarot-readings/) 
  - We will be focusing on the "Three Card Spread".
- [Tarot Reading Basics](https://www.mindbodygreen.com/articles/how-to-do-a-basic-tarot-reading)
  - Includes types of cards and their basic meanings.
- [Rider Waite Tarot Deck](https://www.tarot.com/tarot/decks/rider)
  - This is the deck we will be using (maybe with a different art)
- [Tarot.com](https://www.tarot.com/readings-reports/tarot-readings/celtic-cross)
  - Readings and tarot resources.
- [Evatarot](https://www.evatarot.net/)
  - Tarot Reading Website
- [Tarot Moon](https://tarotmoon.com/)
  - Tarot Reading Website
- [Tarot Card Decks](https://www.yourtarotlife.com/blog/tarot/types-and-use-of-tarot-cards/)
  - Different Tarot Card Decks

