const { app, BrowserWindow, ipcMain, dialog } = require('electron');
const path = require('path');

const { app, ipcMain } = require('electron');
const keytar = require('keytar');

function createWindow() {
  const win = new BrowserWindow({
    width: 1024,
    height: 768,
    webPreferences: {
      contextIsolation: true,
      preload: path.join(__dirname, 'preload.js'),
    },
  });

  if (process.env.NODE_ENV === 'development') {
    
    win.loadURL('http://localhost:5173').catch(console.error);
  } else {
    
    win.loadFile(path.join(__dirname, 'frontend/dist/index.html')).catch(console.error);
  }
}


ipcMain.on('toMain', (event, data) => {
  console.log('Received from React:', data);
  event.sender.send('fromMain', `Electron получи: ${data}`);

  if (data === 'openFile') {
    dialog.showOpenDialog({ properties: ['openFile'] })
      .then(result => {
        event.sender.send('fromMain', result.filePaths);
      });
  }
});

const SERVICE = 'restaurant-service-app';
const ACCOUNT = 'auth-jwt-token';


ipcMain.handle('save-token', async (event, token) => {
  await keytar.setPassword(SERVICE, ACCOUNT, token);
  return true;
});


ipcMain.handle('get-token', async () => {
  const token = await keytar.getPassword(SERVICE, ACCOUNT);
  return token || null;
});


ipcMain.handle('clear-token', async () => {
  await keytar.deletePassword(SERVICE, ACCOUNT);
  return true;
});

app.whenReady().then(createWindow);
