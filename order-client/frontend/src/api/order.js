import { useEffect, useState } from "react";
import request from "./request";
import { getAccessToken } from "../utils/authUtils";
const BASE_URL = 'http://localhost:8083/api/order';


/**
 * @param {number} id
 * @param {string} name
 * 
 * 
 * 
 * 
 */
export const getOrder = async (id) => {
    const name = await getAccessToken(); 
    const result = await request.post(`${BASE_URL}/getOrder`, {name, id});
    return result;
}

export const createOrder = async (data) => {
 const result = await request.post(`${BASE_URL}/create`, data);
 return result;
}

export const updateOrder = async (data) => {
    const result = await request.put(`${BASE_URL}/update`, data);
    return result;
}

export const deleteProduct = async (data) => {
    const result = await request.del(`${BASE_URL}/remove/product`, data);
    return result;
}

export const completeOrder = async (data) => {
    console.log(data);
    const result = await request.post(`${BASE_URL}/complete`, data);
    return result;
}


const orderApi = {
    getOrder,
    createOrder,
    updateOrder,
    deleteProduct,
    completeOrder,



}

export default orderApi;