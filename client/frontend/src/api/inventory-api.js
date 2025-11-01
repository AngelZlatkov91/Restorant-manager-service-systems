import { useEffect, useState } from "react";
import request from "./request";

const BASE_URL = 'http://localhost:8085/api/inventory';

/**
 * @param {number} id
 * @param {string} name
 * @param {string} category
 * @param {number} quantity
 * @param {boolean} active
 * @param {boolean} isCheck
 * 
 * 
 */

export const getAllItems = async () => {
    const result = await request.get(BASE_URL,true);
    const items = Object.values(result);

    return items;
}


const inventoryApi = {
    getAllItems,
}

export default inventoryApi;