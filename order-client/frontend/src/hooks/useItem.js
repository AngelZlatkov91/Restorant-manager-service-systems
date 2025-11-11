import { useEffect, useState } from "react";
import itemApi from "../api/items-api";

export function useGetAllMenuItems() {
    const [items,setItems] = useState([]);
    const fetchItems = async () => {
        try {
            const result = await itemApi.getAllItem();
            setItems(result);
        } catch (err) {
            console.log(err.message);
        }
    };
    useEffect(() => {
        fetchItems();
    },[]);
    

    return [items, fetchItems];

}