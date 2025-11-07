import request from "./request";

const BASE_URL = 'http://localhost:8083/api/check/personal';

/**
 * @param {number} id
 * @param {string} name
 * @param {string} password
 * 
 * 
 * 
 */


export const  login = ( password) => request.post(`${BASE_URL}`, {password});