import categoryApi  from "../api/category-api";
import { useEffect, useState } from "react";

export function useCreateCategory() {
    const category = (categoryName) => categoryApi.createCategory(categoryName);
    
    return category;
}



export function useGetAllCategory() {
  const [categories, setCategories] = useState([]);
  const fetchCategories = async () => {
    try {
      const result = await categoryApi.getAll();
      setCategories(result);
    } catch (err) {
      console.log("Грешка при зареждане на категории:", err.message);
    }
  };

 
  useEffect(() => {
    fetchCategories();
  }, []);

  
  return [categories, fetchCategories];
}

export function useDeleteCategory(id) {

    const categoryId =  categoryApi.deleteCategory(id);
    
    return categoryId;
}
