import axios from "axios";
import { getAccessToken } from "../utils/authUtils";

const api = axios.create({
  baseURL: "", 
});

async function request(method, url, data, useAuth = false) {
  try {
    const config = {
      method,
      url,
      data
    };

    if (useAuth) {
      const token = await getAccessToken();
      if (token) {
        config.headers = {
          ...config.headers,
          Authorization: `Bearer ${token}`,
        };
      }
    }

    const response = await api(config);

    
    if (response.status === 204) return;

    return response.data;

  } catch (err) {
    if (err.response && err.response.data) {
      return err.response.data.message || err.response.data || err.message;
    }
    return err.message;
  }
}

export const get = (url, useAuth = false) => request("GET", url, null, useAuth);
export const post = (url, data, useAuth = false) => request("POST", url, data, useAuth);
export const put = (url, data, useAuth = false) => request("PUT", url, data, useAuth);
export const del = (url, data, useAuth = false) => request("DELETE", url, data, useAuth);

export default {
  get,
  post,
  put,
  del,
};
