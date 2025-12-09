import request from "./request";

/**
 * @param {string} personalName
 * @param {number} totalCostSum
 * 
 * 
 */

export const report = async () => {
    const result = await request.get('http://localhost:8086/api/getRepost');
    const data = Object.values(result);
    return data;
}

const reportApi = {
    
    report,
}

export default reportApi;