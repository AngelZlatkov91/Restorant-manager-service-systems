const { app, BrowserWindow, ipcMain } = require("electron");
const path = require("path");

let loginWindow;
let mainWindow;
let jwtToken = null;

function createLoginWindow() {
  console.log("Creating Login Window...");

  loginWindow = new BrowserWindow({
    width: 600,
    height: 500,
    resizable: false,
    frame: false, 
    show: false, 
    webPreferences: {
      contextIsolation: true,
      preload: path.join(__dirname, "preload.js"),
    },
  });

  
  loginWindow.once("ready-to-show", () => {
    loginWindow.show();
    console.log("Login Window shown");
  });

 
  loginWindow.loadURL("http://localhost:5174/loginPage")
    .catch(err => console.error("Failed to load login URL:", err));

  
  loginWindow.on("closed", () => {
    loginWindow = null;
    console.log("Login Window closed");
  });
}

function createMainWindow() {
  mainWindow = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {
      contextIsolation: true,
      preload: path.join(__dirname, "preload.js"),
    },
  });

  mainWindow.loadURL("http://localhost:5174/");
}

app.whenReady().then(() => {
 
  createLoginWindow();
});

ipcMain.on("loginSuccess", (event, token) => {
  
  jwtToken = token;

  if (loginWindow) {
    loginWindow.close();
    loginWindow = null;
  }

  createMainWindow();
});

ipcMain.on("logout", () => {
  console.log("User logout");

  jwtToken = null;

  if (mainWindow) {
    mainWindow.close();
    mainWindow = null;
  }

  createLoginWindow();
});



ipcMain.handle("getToken", async () => jwtToken);

app.on("window-all-closed", () => app.quit());

