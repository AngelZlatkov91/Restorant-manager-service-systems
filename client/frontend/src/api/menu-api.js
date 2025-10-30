import request from "./request";

const BASE_URL = 'http://localhost:8082/api/menu/item';

/**
 * 
 * @param {string} name
 * @param {number} price
 * @param {string} category
 * @param {string} typeProduct
 * 
 * 
 * 
 */


export const  createMenuItem =  (data) => {
    console.log(data);
    request.post(`${BASE_URL}/create`,data,true);
    
}


const menuApi = {
    createMenuItem,
}

export default menuApi;