import { useEffect, useState } from "react";
import request from "./request";
import { getAccessToken } from "../utils/authUtils";
const BASE_URL = 'http://localhost:8083/api/order/getAll';

/**
 * @param {number} id
 * @param {string} name
 * 
 * 
 * 
 * 
 */
export const getAllActiveOrder = async () => {
    const name = await getAccessToken();
    const result = await request.post(`${BASE_URL}`, {name});
    const tables = Object.values(result);
    console.log(tables);
    return tables;
}


const orderApi = {
    getAllActiveOrder,

}

export default orderApi;