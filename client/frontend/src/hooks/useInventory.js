import { useEffect, useState } from "react";
import inventoryApi from "../api/inventory-api"
export function useInventoryItems () {
    const [items, setItems] = useState([]);

    const fetchItems = async () => {
        try {
            const result = await
            inventoryApi.getAllItems();
            console.log(result);
            setItems(result);
        } catch (err) {
            console.log(err.message);
        }
    };

    useEffect(() => {
        fetchItems();
    })

    return [items, fetchItems]

}