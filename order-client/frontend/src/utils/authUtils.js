
import jwt_decode from "jwt-decode";

export async function getAccessToken() {
  return await window.electronAPI.getToken();
}

export function decodeJWT(token) {
  return jwt_decode(token);
}
