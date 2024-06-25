## Context and Problem Statement
How are we gonna store userdata in online, non-local storage?

## Considered Options
* Use Firebase's RealTimeDatabase to store everything as one json file
* Use a NoSQL database on google cloud
* Use Firestore

Decision
Store user data in firestore documents in a "users" collection with the users uids 
as the document names