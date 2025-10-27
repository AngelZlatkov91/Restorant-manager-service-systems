import categoryApi  from "../api/category-api";
import { useEffect, useState } from "react";

export function useCreateCategory() {
    const category = (categoryName) => categoryApi.createCategory(categoryName);
    
    return category;
}



export function useGetAllCategory() {
 const [category, setCategories] = useState([]);
    useEffect(() => {
         (async () => {
            try {
                
                const result = await categoryApi.getAll();
                setCategories(result)
                console.log(result);
            } catch (err) {
               console.log(err.message);
               
            }
            
         })();
    },[])

    return [category, setCategories];
}
