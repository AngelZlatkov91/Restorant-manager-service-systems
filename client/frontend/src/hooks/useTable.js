import { useEffect, useState } from "react";

import tableApi from "../api/table-api";

export function useCreateTable() {
    const table = (data) => tableApi.createTable(data);

    return table;
}

export function useGetAllTable() {
    const [tables, setTables] = useState([]);
    const fetchTables = async () => {
        try {
         const table = await tableApi.getAllTable();
         setTables(table);
        } catch (err) {
           console.log(err);
        }
    };

    useEffect(() => {
        fetchTables();
    },[]);
    
     return[tables, fetchTables]
}

export function useDeleteTable(id) {
    const result = tableApi.deleteTable(id);
    return result;
}
