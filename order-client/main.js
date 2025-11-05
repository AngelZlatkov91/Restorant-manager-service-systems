const { app, BrowserWindow, ipcMain, dialog } = require('electron');
const path = require('path');
const isDev = process.env.NODE_ENV === 'development';

let jwtToken = null; 
const fs = require('fs');
const preloadPath = path.join(process.cwd(), 'preload.js');
console.log("Looking for preload at:", preloadPath);
console.log("Exists?", fs.existsSync(preloadPath));

function createWindow() {
  const win = new BrowserWindow({
    width: 1024,
    height: 768,
    webPreferences: {
      contextIsolation: true,
      sandbox: false, 
      nodeIntegration: false,
      preload: path.resolve(__dirname, 'preload.js'),
    },
  });

  if (isDev) {
    const devServerURL = process.env.VITE_DEV_SERVER_URL || 'http://localhost:5174';
    console.log("Loading dev server:", devServerURL);
    win.loadURL(devServerURL).catch(console.error);
    win.webContents.openDevTools();
  } else {
    win.loadFile(path.join(__dirname, 'frontend/dist/index.html')).catch(console.error);
  }
  
}


ipcMain.on('toMain', (event, data) => {
  console.log('Received from React:', data);
  event.sender.send('fromMain', `Electron получи: ${data}`);

  if (data === 'openFile') {
    dialog.showOpenDialog({ properties: ['openFile'] })
      .then(result => event.sender.send('fromMain', result.filePaths));
  }
});


ipcMain.on('saveToken', (event, token) => {
  jwtToken = token;
});

ipcMain.handle('getToken', async () => {
  return jwtToken;
});

app.whenReady().then(createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit();
});

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) createWindow();
});
