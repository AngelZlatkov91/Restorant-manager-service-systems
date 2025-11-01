import { useEffect, useState } from "react";
import menuApi from "../api/menu-api";

export function useCreateMenuItem() {
    const menuItem = (data) => menuApi.createMenuItem(data);
    
    return menuItem;
}

export function useGetAllMenuItems() {
    const [items,setItems] = useState([]);
    const fetchItems = async () => {
        try {
            const result = await menuApi.getAllItem();
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

export function useDeleteMenuItem(id) {
    const result = menuApi.deleteMenuItem(id);
    return result; 
    
}

export function useGetItemById(id) {
const [item, setItem] = useState({
        id: '',
        name: '',
        price:'',
        category: '',
        typeProduct: '',
        active: Boolean,
    });

    useEffect(() => {
        (async () => {
            try {
                 const result =await menuApi.getMenuItemById(id);
                 setItem(result);
            } catch (err) {

            }
        })();
    },[id])
    return [item,setItem];
}

export function useUpdateItem(data) {
    const result = menuApi.updateItem(data);
    return result;
}