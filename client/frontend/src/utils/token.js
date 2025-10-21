import { ipcRenderer } from 'electron';

export async function saveToken(token) {
  await ipcRenderer.invoke('save-token', token);
}

export async function getToken() {
  return await ipcRenderer.invoke('get-token');
}

export async function clearToken() {
  await ipcRenderer.invoke('clear-token');
}
