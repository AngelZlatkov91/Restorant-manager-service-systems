import request from "./request.js";

const BASE_URL = 'http://localhost:8082/api/menu/category';

/**
 * 
 * @param {string} categoryName 
 * 
 * 
 * 
 * 
 * 
 */

export const  createCategory = (categoryName) => {
  
    request.post(`${BASE_URL}/create`, categoryName,true);
};

export const getAll = async () => {
    
   const result = await request.get(`${BASE_URL}/getAll`,true);
   const categories = Object.values(result);
   return categories;
}

const categoryApi = {
    createCategory,
    getAll,
}

export default categoryApi;
