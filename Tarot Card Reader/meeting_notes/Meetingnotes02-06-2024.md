Previous meeting notes:
[[Meetingnotes26-05-2024]{.underline}](https://docs.google.com/document/d/1fT4_r4IxmW3OYe8u1LqJa4DmAhkzetVSoEuRTbPQZ8g/edit)

## Retrospective

-   Severless functions are not necessarily simpler than self hosting(or
    cheaper at scale), they are advertised as such but we mainly used
    them because they are free (google wants you to use them). It
    introduced complicated orchestration issues that we did not
    anticipate being so difficult. The way authentication works and
    google-cloud vs firebase-client vs firebase-admin sdks that cloud
    functions can use do not play well with each other which is easy to
    forget and is not mentioned/warned in tutorials.

## Work Plan

-   Add to User Story 4

    -   Need offline backups (offline responses)

-   Fixing CI/CD "Test Coverage"

-   History, We'll need ADR for decisions on how stuff is stored in
    firestore

-   Setup CLI for those who want to work on the backend (arjun, Hieu,
    chris)

-   Bug fixing in UI

-   Documentation run

## APIs

We have decided to use Firebase Cloud Functions since they are included
as a Firebase service for free with a giant free tier

### History api

Users should not be able to write to Firestore directly. We need api
endpoints (firebase functions) to handle database writes at a minimum.
and take measures to not allow bad info to be written to the database

## New Features To Consider

### Further Accessibility Features

-   Colorblind/High contrast mode

### Changing the background color

### History Tab

Store only a weeks worth of fortunes

Separate daily fortune history?

Firestore?
