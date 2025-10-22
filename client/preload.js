const { contextBridge, ipcRenderer } = require("electron");

contextBridge.exposeInMainWorld("electronAPI", {
  sendMessage: (channel, data) => {
    if (channel === "toMain") ipcRenderer.send(channel, data);
  },
  onMessage: (channel, callback) => {
    if (channel === "fromMain") {
      ipcRenderer.on(channel, (event, data) => callback(data));
    }
  },
  saveToken: (token) => ipcRenderer.send("saveToken", token),
  getToken: () => ipcRenderer.invoke("getToken")
});
