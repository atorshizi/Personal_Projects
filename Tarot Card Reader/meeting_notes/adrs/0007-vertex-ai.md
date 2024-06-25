# Generate daily fortunes based on cards 
Status: accepted 

## Context and Problem Statement
We want to show unique daily fortunes for each users that do not get repetative. 

## Considered Options
* use openAI api to generate fortunes 

* use vertexAI api to generate fortunes 

* pregenerate a list of fortunes for each combination of cards 

## Decision Outcome
User vertexAI to generate fortunes 

## Pros and Cons 
* Pro - Free to use  
* Pro - Easy to incorporate 
* Pro - Gives plausible fortunes that match the cards 
* Con - might be unreliable if api goes down (we chose to have pregenerated fallback responses in this case) 
* Con - slows down the website every time a fortune is generated 