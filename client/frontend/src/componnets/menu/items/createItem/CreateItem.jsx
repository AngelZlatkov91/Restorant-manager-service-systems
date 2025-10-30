import { useGetAllCategory } from "../../../../hooks/useCategory";
import { useCreateMenuItem } from "../../../../hooks/useItem";
import { useForm } from "../../../../hooks/useForm";

const initialValues = {
  name: '',
  price: '',
  category: '',
  typeProduct: '',
}

export default function CreateItem() {
  const optionTypeProduct = [
    {value: "BAR", label: "BAR" },
    {value: "KITCHEN", label: "KITCHEN"}
  ]

  const [categories, refreshCategories] = useGetAllCategory();
  
   const createMenuItem = useCreateMenuItem();
   const createHandler = async(values) => {
    try {
      const menuItemCreate = await createMenuItem(values);
      console.log(menuItemCreate);

    } catch (err) {
      console.log(err.message);
    }
   }

   const {
    values,
    changeHandler,
    submitHandler,
   } = useForm(initialValues,createHandler);
      
   return (
        <>
          <section id="create-page" className="auth">
            <form id="create" onSubmit={submitHandler}>
                <div className="container">

                    <h1>Create Item</h1>
                    <label htmlFor="leg-title">Item Name:</label>
                    <input 
                    type="text" 
                    id="name" 
                    name="name" 
                    value={values.name}
                    onChange={changeHandler}
                    placeholder="Enter item name..." />

                    <label htmlFor="price">Price:</label>
                    <input 
                    type="number" 
                    id="price" 
                    name="price"
                    value={values.price}
                    onChange={changeHandler}
                     placeholder="Enter item price" />

                    <label htmlFor="category">Category</label>
                    <select value={categories} onChange={changeHandler}>
                    <option value="">Categories</option>
                    {categories.map((cat) => (
                      <option key={cat.id} value={cat.category}>
                        {cat.category}
                      </option>
                    ))}
                     </select>
                    <label htmlFor="typeProduct">Type Product</label>
                    <input
                    type="text"
                    name="typeProduct" 
                    id="typeProduct"
                    value={values.typeProduct}
                    onChange={changeHandler}
                    />
                    <input className="btn submit" type="submit" value="Create Item" />
                </div>
            </form>
         </section>
       </>
  );
}