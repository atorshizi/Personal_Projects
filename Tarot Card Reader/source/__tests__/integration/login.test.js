const puppeteer = require('puppeteer');

function mockGetAuth() {
  return 'mockAuth123'; 
} 

function mockSignInWithEmailAndPassword(auth,user,password) {
  if (auth == 'mockAuth123') { 
    if (user == 'mock@gmail.com' && password == 'mocking123') { 
      return new Promise((resolve) => {resolve('success');}); 
    }
    return new Promise((reject) => {reject('Invalid Creds!');}); 
  } else { 
    return new Promise((reject) => {reject('Invalid Auth!');}); 
  }
}

describe('Login Scenario', () => {
  // Visit the web app
  let browser;
  let page;

  beforeAll(async () => {
    // Launch the browser and create a new page
    browser = await puppeteer.launch({
      headless: true,  // Ensure Puppeteer runs in headless mode
      args: ['--no-sandbox', '--disable-setuid-sandbox', '--disable-gpu', '--single-process']
    });
    page = await browser.newPage();
    await page.goto('http://127.0.0.1:5500/source/login.html');
  });

  afterAll(async () => {
    if (browser) {
      await browser.close();
    }
  });
  
  it('Incorrect Email', async () => { 
    // await page.waitForSelector('#login', {visible: true});
    // await page.waitForSelector('#password', {visible: true});
    await page.evaluate( () => document.getElementById('login').value = ''); 
    await page.evaluate( () => document.getElementById('password').value = ''); 
    await page.type('input#login', 'fail@gmail.com'); 
    var email = await page.$eval('input#login', input => input.value);

    await page.type('input#password', 'mocking123'); 
    var pass = await page.$eval('input#password', input => input.value);

    const auth = mockGetAuth(); 
    var message = ''; 
    await mockSignInWithEmailAndPassword(auth, email, pass).then((result) => {
      // Signed in 
      message = result; 
    }).catch((error) => {
        console.error(error); 
      message = 'failed'; 
    }); 
    await expect(message).toBe('Invalid Creds!'); 
  }, 10000); 

  it('Incorrect Password', async () => { 
    await page.evaluate( () => document.getElementById('login').value = ''); 
    await page.evaluate( () => document.getElementById('password').value = ''); 
    await page.type('input#login', 'mock@gmail.com'); 
    var email = await page.$eval('input#login', input => input.value);

    await page.type('input#password', 'mocking1234'); 
    var pass = await page.$eval('input#password', input => input.value);

    const auth = mockGetAuth(); 
    var message = ''; 
    await mockSignInWithEmailAndPassword(auth, email, pass).then((result) => {
      // Signed in 
      message = result; 
    }).catch((error) => {
        console.error(error); 
      message = 'failed'; 
    }); 
    await expect(message).toBe('Invalid Creds!'); 
  }); 

  it('Correct values', async () => { 
    await page.evaluate( () => document.getElementById('login').value = ''); 
    await page.evaluate( () => document.getElementById('password').value = ''); 
    await page.type('input#login', 'mock@gmail.com'); 
    var email = await page.$eval('input#login', input => input.value);

    await page.type('input#password', 'mocking123'); 
    var pass = await page.$eval('input#password', input => input.value);

    const auth = mockGetAuth(); 
    var message = ''; 
    await mockSignInWithEmailAndPassword(auth, email, pass).then((result) => {
      // Signed in 
      message = result; 
    }).catch((error) => {
        console.error(error);
      message = 'failed'; 
    }); 
    await expect(message).toBe('success'); 
  }); 
}); 