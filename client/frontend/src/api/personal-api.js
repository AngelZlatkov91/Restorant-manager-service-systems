import request from "./request";

const BASE_URL = 'http://localhost:8083/api/personal';

/**
 * @param {number} id
 * @param {string} name
 * @param {string} password
 * 
 * 
 * 
 */

export const createPersonal = (data) => {
    const result = request.post(`${BASE_URL}/create`, data, true);
    return result;
}

export const getAllPersonal = async () => {
    const result = await request.get(`${BASE_URL}`, true);
    return result;
}



const personalApi = {
    createPersonal,
    getAllPersonal,
}

export default personalApi;

