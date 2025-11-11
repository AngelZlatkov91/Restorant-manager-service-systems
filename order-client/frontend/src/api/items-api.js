import request from "./request";



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

export const getAllItem = async () => {
    const result = await request.get('http://localhost:8082/api/getAll');
    const items = Object.values(result);
    return items;
}

const itemApi = {
    
    getAllItem,
}

export default itemApi;