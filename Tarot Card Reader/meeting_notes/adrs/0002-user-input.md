# Select user input from preselected questions

## Context and Problem Statement

What kind of user input are we going to have?

## Considered Options

* Generic text box
* Speech synthesis
* Dropdown selector of preset questions

## Decision Outcome

Chosen option: Dropdown selector of preset questions, this option was decided on because it limits the user's freedom in what they are able to input into the fortune teller. The main concern is that further down the line when we want to implement response generation through the use of generative AI, we want to enhance security by making sure the user cannot inject harmful prompts to the AI, which cause issues for our web app. 
