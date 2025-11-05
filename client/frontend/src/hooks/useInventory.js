import { useEffect, useState } from "react";
import inventoryApi from "../api/inventory-api"
export function useInventoryItems () {
    const [items, setItems] = useState([]);

    const fetchItems = async () => {
        try {
            const result = await
            inventoryApi.getAllItems();
            setItems(result);
        } catch (err) {
            console.log(err.message);
        }
    };

    useEffect(() => {
        fetchItems();
    },[]);

    return [items, fetchItems]

}

export function useUpdateInventoryItem (data) {
 const result = inventoryApi.updateItemQuantity(data);
 return result;
}

export function useGetItemFromInventory (id) {
    const [item, setItem] = useState({
                id:'',
                name: '',
                category: '',
                quantity: '',
                active: Boolean,
                check: Boolean,
    });

    useEffect(() => {
        (async () => {
            try {
                 const result = await inventoryApi.getItemByIdFromInventory(id);
                 setItem(result);
            } catch(err) {
                console.log(err.message);
            }
        })();
    },[id])
    
    return [item, setItem];
}