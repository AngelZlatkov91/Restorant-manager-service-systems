import request from "./request.js";

const BASE_URL = 'http://localhost:8082/api/menu/category';

/**
 * 
 * @param {string} categoryName 
 * @param {string} id
 * 
 * 
 * 
 * 
 */

export const  createCategory = (categoryName) => {
   const result = request.post(`${BASE_URL}/create`, categoryName,true);
   return result;
};

export const getAll = async () => {
    
   const result = await request.get(`${BASE_URL}/getAll`,true);
   const categories = Object.values(result);
   return categories;
}
export const deleteCategory = (id) => {
   
 request.del(`${BASE_URL}/delete/${id}`,null,true);
}

const categoryApi = {
    createCategory,
    getAll,
    deleteCategory,
}

export default categoryApi;
