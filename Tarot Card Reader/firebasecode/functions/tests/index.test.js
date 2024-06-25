const test = require("firebase-functions-test")({
  projectId: "cse-112-tarot-card-reader",
}, "/Users/torinfaes/Documents/firebaseapikeys/"+
"cse-112-tarot-card-reader-a8ae9eb5b78b.json");
const {before, after} = require("mocha");
// after firebase-functions-test has been initialized
describe("Cloud Functions", () => {
  let myFunctions;
  before(() => {
    myFunctions = require("../index.js");
  });
  after(() => {
    test.cleanup();
  });
  describe("genFortune", () => {
    it("should return a fortune", async () => {
      const wrapped = test.wrap(myFunctions.genFortune);
      const data = {cardIndex1: 3, cardIndex2: 4, cardIndex3: 5};
      try {
        const fortune = await wrapped({data: data});
        console.log(fortune);
        // successful test
      } catch (err) {
        console.error(err);
        // failed test
        throw err;
      }
    });
    it("should return an error if no card numbers are provided", async () => {
      const wrapped = test.wrap(myFunctions.genFortune);
      try {
        const fortune = await wrapped({data: {}});
        console.log(fortune);
        // failed test
        throw new Error("Function did not throw an error");
      } catch (err) {
        console.error(err);
        // successful test
        return "success";
      }
    });
  });
});


