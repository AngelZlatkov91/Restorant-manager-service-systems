import request from "./request";

const BASE_URL = 'http://localhost:8083/api/order';

export const fetchOrders = async() => {
   const result = await request.get(`${BASE_URL}/getAll`);
    return result;
};