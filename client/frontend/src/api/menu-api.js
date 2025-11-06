import { useEffect, useState } from "react";
import request from "./request";

const BASE_URL = 'http://localhost:8082/api/menu/item';

/**
 * @param {string} id
 * @param {string} name
 * @param {number} price
 * @param {string} category
 * @param {string} typeProduct
 * @param {boolean} active
 * 
 * 
 * 
 */


export const  createMenuItem =  (data) => {
   const result =  request.post(`${BASE_URL}/create`,data,true);
    return result;
}

export const getAllItem = async () => {
    const result = await request.get('http://localhost:8082/api/getAll');
    const items = Object.values(result);
    return items;
}

export const deleteMenuItem = (id) => {
   const result = request.del(`${BASE_URL}/delete/${id}`, null, true);
   return result;
}

export const getMenuItemById = (id) => {    
    const result = request.post(`${BASE_URL}/itemId`,id,true);
    return result;
}

export const updateItem = (data) => {
    const result = request.put(`${BASE_URL}/update`,data,true);
    return result;
}





const menuApi = {
    createMenuItem,
    getAllItem,
    deleteMenuItem,
    getMenuItemById,
    updateItem,
}

export default menuApi;