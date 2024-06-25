# Keep track of user's daily fortunes 
Status: accepted 

## Context and Problem Statement
We want to make sure each user can save their daily fortunes and access them later in the week and also make sure they only get one daily fortune per day. 

## Considered Options
* Store fortunes locally and treat each device as a user 
    - each day every device gets a new allowed fortune 

* Make users login and tie the user fortunes to the accounts 
    - each email gets a new daily fortune every day 

## Decision Outcome
User accounts and logging in was chosen to track users fortunes and to limit daily fortunes 

## Pros and Cons 
* Pro - users can access fortunes from any platform 
* Pro - more accurate since the same device can be used by multiple users 
* Pro - protects each users fortunes from other users 
* Con - extra work for the user to create an account - might drive them to not use the daily fortune feature 