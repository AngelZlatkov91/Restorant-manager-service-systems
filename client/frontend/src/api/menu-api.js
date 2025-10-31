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
   const result = request.del(`${BASE_URL}/delete`, id, true);
   return result;
}



const menuApi = {
    createMenuItem,
    getAllItem,
    deleteMenuItem,
}

export default menuApi;