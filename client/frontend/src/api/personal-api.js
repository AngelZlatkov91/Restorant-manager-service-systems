import request from "./request";

const BASE_URL = 'http://localhost:8083/api/personal';

/**
 * @param {number} id
 * @param {string} name
 * @param {string} password
 * @param {string} role
 * @param {boolean} active
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

export const changePersonalStatus = async (id) => {
    const result = await request.get(`${BASE_URL}/change/status/${id}`, true);
    return result;
}

export const getPersonalById = (id) => {
    const result = request.get(`${BASE_URL}/${id}`, true);
    return result;
}



const personalApi = {
    createPersonal,
    getAllPersonal,
    changePersonalStatus,
    getPersonalById,
}

export default personalApi;

