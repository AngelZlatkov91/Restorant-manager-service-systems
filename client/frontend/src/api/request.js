import { getAccessToken } from "../utils/authUtils";

 async function request(method, url , data, useAuth = false) {
    const options = {}; 
        
    
    if (method !== 'GET') {
        options.method = method;
    }
    if (data) {
        options.headers = {
          ...options.headers,
            'Content-Type': 'application/json',
        };

        options.body = JSON.stringify(data);
    }
    if (useAuth) {
      const token = await getAccessToken();
      
      if (token) {
        options.headers = {
          ...options.headers,
          'Authorization': `Bearer ${token}`,
        }
      }
    }

    try {
      const response =   await fetch(url, options);
      if (response.status === 204) {
          return;
      }
     const result = await response.json();
      return result;

    } catch (err) {
       return err.message;
    }
  
}; 

export const get = (url, useAuth = false) => request('GET', url, null, useAuth);
export const post = (url, data, useAuth = false) => request('POST', url, data, useAuth);
export const put = (url, data, useAuth = false) => request('PUT', url, data, useAuth);
export const del = (url, data,useAuth = false) => request('DELETE', url, data, useAuth);


export default {
    get,
    post,
    put,
    del,
}