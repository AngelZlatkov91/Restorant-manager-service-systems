const { app, BrowserWindow, ipcMain } = require("electron");
const path = require("path");

let loginWindow;
let mainWindow;
let jwtToken = null;

function createLoginWindow() {
  loginWindow = new BrowserWindow({
    width: 400,
    height: 300,
    resizable: false,
    frame: false, // прави го като kiosk/popup
    webPreferences: {
      contextIsolation: true,
      preload: path.join(__dirname, "preload.js"),
    },
  });

  loginWindow.loadURL('http://localhost:5174/loginPage');
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

ipcMain.handle("getToken", async () => jwtToken);

app.on("window-all-closed", () => app.quit());
