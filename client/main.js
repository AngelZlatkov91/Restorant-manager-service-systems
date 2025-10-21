const { app, BrowserWindow, ipcMain, dialog } = require('electron');
const path = require('path');

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

app.whenReady().then(createWindow);
