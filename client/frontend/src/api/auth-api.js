import request from "./request.js";

const BASE_URL = 'http://localhost:8080/api/user';

/**
 * 
 * @param {string} username 
 * @param {string} password 
 * @param {string} confirmPassword
 * 
 * 
 */

export const  login = (username, password) => request.post(`${BASE_URL}/login`, {username, password});



export const register = (username, password,confirmPassword) => request.post(`${BASE_URL}/register`, {username, password, confirmPassword});

// export const logout = () => request.get(`${BASE_URL}/logout`);