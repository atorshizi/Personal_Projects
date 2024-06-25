
const puppeteer = require('puppeteer');

describe('Basic user flow for Website', () => {
  let browser;
  let page;

  beforeAll(async () => {
    browser = await puppeteer.launch({
      headless: true,  // Ensure Puppeteer runs in headless mode
      args: ['--no-sandbox', '--disable-setuid-sandbox', '--disable-gpu', '--single-process']
    });
    page = await browser.newPage();
    await page.goto('http://127.0.0.1:5500/source/index.html');
  });

  afterAll(async () => {
    if (browser) {
      await browser.close();
    }
  });
  

  // Next user will generate a fortune 
  it('Generate a fortune', async () => {
    console.log('Generating a fortune...');
    // Select and click generate button
    await page.waitForSelector('#generate-btn', {visible: true});

    const generateButton = await page.$('#generate-btn');
    await generateButton.click();

    // Select the text of the fortune
    await page.waitForSelector('#meaning', {visible: true});
    const fortuneReading = await page.$('#meaning');

    // const displayPropertyValue = await fortuneReading.getProperty('style');
    // const displayValueHandle = await displayPropertyValue.getProperty('display');
    // const displayValue = await displayValueHandle.jsonValue();
    const displayValue = await page.evaluate(element => element.style.display, fortuneReading);

    // Expect the `display` property value to be "block"
    expect(displayValue).toBe('block');
  });

  // User will save the fortune
  it('Saving a fortune', async () => {
    console.log('Saving a fortune...');
    // Select and click save button
    const saveButton = await page.$('#save');
    await saveButton.click();

    // Get local storage
    const currentStorage = await page.evaluate(() => {
      return localStorage.getItem('readings');
    });

    // Expect local storage to not be empty
    expect(currentStorage !== '[]').toBe(true);
  });

  // User check history
  it('Checking History', async () => {
    console.log('Checking History...');
    await page.waitForSelector('#nav-btn-history', {visible: true});
    const historyButton = await page.$('#nav-btn-history');
    await historyButton.click();
    
    // Wait for the history item button to appear and click it
    await page.waitForSelector('.history-item-btn-display', {visible: true});
    const histDisplayButton = await page.$('.history-item-btn-display');
    await histDisplayButton.click();

    // Wait for the #meaning section to be visible and check if it has content
    await page.waitForSelector('#meaning', {visible: true});
    const content = await page.evaluate(() => {
        const element = document.querySelector('#meaning');
        return element ? element.textContent.trim().length > 0 : false;
    });

    expect(content).toBe(true); // Expect that there is significant content
});


  // User deletes fortune from history
  // it('Deleting a fortune', async () => {
  //   console.log('Navigating to History tab...');
  //   // Ensure the navigation to the history tab is done
  //   await page.waitForSelector('#nav-btn-history', {visible: true});
  //   const historyButton = await page.$('#nav-btn-history');
  //   await historyButton.click();

  //     console.log('Deleting a fortune...');
  //     // Select and click delete button
  //     await page.waitForSelector('.history-item-btn-delete', {visible: true});
  //     const deleteButton = await page.$('.history-item-btn-delete');
  //     await deleteButton.click();

  //     // Get local storage
  //     await page.waitForSelector('readings', {visible: true});

  //     const currentStorage = await page.evaluate(() => {
  //       return localStorage.getItem('readings');
  //     });

  //     // Expect local storage to be empty
  //     expect(currentStorage).toBe('[]');
  // });


  it('Deleting a fortune', async () => {
    console.log('Navigating to History tab...');
    // Ensure the navigation to the history tab is done
    await page.waitForSelector('#nav-btn-history', {visible: true});
    const historyButton = await page.$('#nav-btn-history');
    await historyButton.click();
  
    console.log('Deleting a fortune from history...');
    // Wait for the delete button to be visible in the history tab
    await page.waitForSelector('.history-item-btn-delete', {visible: true});
    const deleteButtons = await page.$$('.history-item-btn-delete');
  
    if (deleteButtons.length > 0) {
      await deleteButtons[0].click(); // Adjust index as needed based on which item to delete
  
      // Optional: Check the UI for updates or check local storage
      const storageAfterDelete = await page.evaluate(() => {
        return localStorage.getItem('readings');
      });
      console.log('Storage after delete:', storageAfterDelete);
  
      // Expect local storage to be empty or to reflect deletion
      expect(storageAfterDelete).toBe('[]');
    } else {
      throw new Error('No delete buttons found');
    }
  }, 10000); // Extended timeout for this test
  
  
  // User returns to homepage
  it('Return to homepage', async () => {
     console.log('Returning to home...');
     // Select and click home button
     
     const homeButton = await page.$('#nav-btn-home');
     await homeButton.click();

     // Expect local storage to be empty
     const currentStorage = await page.evaluate(() => {
       return localStorage.getItem('readings');
     });

     // Expect local storage to be empty
     expect(currentStorage).toBe('[]');
  });

});

