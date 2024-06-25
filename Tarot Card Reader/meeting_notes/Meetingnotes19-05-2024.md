Previous meeting notes:
[[Meetingnotes12-05-2024]{.underline}](https://docs.google.com/document/d/1n_uG_xyDxXdxJbXERCrEsGW6m7shc_thty1iefwIbww/edit?usp=sharing)

## Retrospective

-   Some confusion with the Backend architecture. Good to all be on the
    same page with big architectural decisions, we decided on node apis

-   We gained lots of understanding of how apis and severless features
    work.

## Work Plan

-   Finish User Story 5

-   Add to User Story 4

    -   Need ai facade

    -   Need to hide actual ai prompting for security

    -   Need offline backups (offline responses)

    -   alternate AI options (chatgpt, claude)?

## APIs

### Hidden api key api

Instead of having the api key public, it is served with a serverless
api.

### AI api

Instead of allowing the client to construct whatever prompt they want in
client side javascript, we have an api that takes in 3 constricted card
values, constructs the prompt, and performs Firestore caching,
eventually fallback cached fortunes.

### Static hosting

Firebase static webpage hosting.

## New Features

### Changing the background color

### History Tab

Store only a weeks worth of fortunes

Separate daily fortune history?

Firestore?
