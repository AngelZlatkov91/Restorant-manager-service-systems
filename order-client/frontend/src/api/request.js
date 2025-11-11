import axios from "axios";
import { toast } from "react-toastify";
const api = axios.create({
  baseURL: "", 
});

async function request(method, url, data) {
  try {
    const config = {
      method,
      url,
      data
    };
 
    const response = await api(config);

    
    if (response.status === 204) return;

    return response.data;

  } catch (err) {
    let message = "Unknown error";

    if (err.response?.data?.message) {
      message = "You must field the fields!"
    } else if (err.response?.data) {
      message = err.response.data;
    } else if (err.message) {
      message = err.message;
    }


    toast.error(message);

    
    return message;
  }
}

export const get = (url) => request("GET", url, null);
export const post = (url, data) => request("POST", url, data);
export const put = (url, data) => request("PUT", url, data);
export const del = (url, data) => request("DELETE", url, data);

export default {
  get,
  post,
  put,
  del,
};
