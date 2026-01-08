import { useEffect, useState } from "react";
import request from "./request";

const BASE_URL = 'http://localhost:8083/api/table';

/**
 * @param {number} id
 * @param {string} tableName
 * @param {string} owner
 * @param {boolean} empty
 * 
 * 
 * 
 */


export const createTable = (data) => {
    const result = request.post(`${BASE_URL}/create`,data,true);
    return result;
}

export const deleteTable = (id) => {
    const result = request.del(`${BASE_URL}/delete/${id}`,null,true);
    return result;
}

export const getAllTable = async () => {
    const result = await request.get(`http://localhost:8083/api/check/table`);   
    const tables = Object.values(result);
    return tables;
}

export const getOneTable = (id) => {
    const result = request.get(`${BASE_URL}/${id}`, true);
    return result;
}

const tableApi = {
    createTable,
    deleteTable,
    getAllTable,
    getOneTable,
}

export default tableApi;