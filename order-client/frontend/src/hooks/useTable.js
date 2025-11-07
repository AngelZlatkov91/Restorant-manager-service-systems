import { useEffect,useState } from "react";
import tableApi from "../api/tables-api";


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