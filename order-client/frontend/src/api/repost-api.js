import request from "./request";
import { getAccessToken } from "../utils/authUtils";

/**
 * @param {string} personalName
 * @param {number} totalCostSum
 * 
 * 
 */

export const report = async () => {
    const token = await getAccessToken(); 

    const result = await request.post('http://localhost:8086/api/getRepost',{personalName: token.name});
    
    return result;
}

export const isCheck = async (id) => {
    const result = await request.get(`http://localhost:8086/api/getRepost/${id}`);
    return result;
}

const reportApi = {
    
    report,
    isCheck,
}

export default reportApi;