import { useGetAllCategory } from "../../../hooks/useCategory";

export default function Category() {
   const [category] = useGetAllCategory();
   
   console.log(category);

   return (
      <> 
         
      
      </>
  );
}