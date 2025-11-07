import request from "./request";

export const getAllTable = async () => {
    const result = await request.get(`http://localhost:8083/api/check/table`);   
    const tables = Object.values(result);
    return tables;
}


const tableApi = {
    getAllTable,
}

export default tableApi;