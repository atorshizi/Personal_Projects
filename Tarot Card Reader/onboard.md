Our application is a web app with a firebase backend. 
## To setup a development environment:
* clone the repo
* run `npm install .` to install dev dependencies
* frontend tests run with `npm test`
* Deploying the static frontend files to firebase static hosting is done via the [firebase-cli](##Firebase-cli)
  

## Firebase-cli
To setup the application locally after the cloud function has been merged to dev, you’ll have to follow the following steps:

1. Navigate to firebasecode/functions and run 
    'npm install'. 
2. Run ‘npm install -g firebase-tools’ to install the Firebase CLI
3. Run ‘firebase login’ to authenticate the CLI. ( Its possible that you might already be logged in. If not, use the email that is added to the firebase project.  If you aren’t in the firebase project yet, someone who’s already in the firebase can add you. )
4. Download the cse-112-tarot-card-reader-firebase-adminsdk-njtc0-59648db4b2.json file from the #term-project channel and place it anywhere OUTSIDE THE PROJECT DIRECTORY
5. Set the app credentials with the following command based on platform: 
    (On Unix): export GOOGLE_APPLICATION_CREDENTIALS="path/to/key.json" 
    (On Windows): set GOOGLE_APPLICATION_CREDENTIALS=path\to\key.json
    
    The path should be the path to the cse-112-tarot-card-reader-a8ae9eb5b78b.json file
6. Uncomment the connectFunctionsEmulator function call in firebaseInit.js. This sends all function requests through the emulator instead of the deployed functions. If you would like to test with the deployed functions, comment out this function call. Before pushing any changes, always comment out this line.  
7. Run ‘firebase emulators:start’ to start the cloud function emulator. The emulator makes sure the cloud function is run locally. 
8. Ensure that the functions and firestore emulators are both running in the emulatorUI before testing. Note that Authentication is not emulated, so delete any users created during local testing through the firebase project site.

If you are testing the application locally past the Jun 7th version of ‘dev’, always run the emulator using firebase emulators:start before running index.html. 

## Deploying to Production
From the project directory.



```cd ./firebasecode```

Install necessary firebase npm packages

```npm install .```

Create a symlink from the project's frontend files to firebase directory if you haven't already

``` ln -s ../source ./source ```

Deploy using the firebase-cli

```firebase deploy```

This will deploy the firebase functions in ./functions and any files in ./source (which is a symlink to [PROJECT_ROOT]/source)

