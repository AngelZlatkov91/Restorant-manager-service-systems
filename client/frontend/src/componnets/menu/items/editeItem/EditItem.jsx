
import { useParams, useNavigate } from "react-router-dom";
import { useGetAllCategory } from "../../../../hooks/useCategory";
import { useGetItemById, useUpdateItem } from "../../../../hooks/useItem";
import {useForm} from "../../../../hooks/useForm"
export default function EditItem() {
  const { id } = useParams(); 
  const navigate = useNavigate();
  const [categories] = useGetAllCategory();
  const [item, setItem] = useGetItemById(id);
  
  const updateHandler = async (values) => {
    try {
         const menuItemUpdate = useUpdateItem(values);
         navigate("/getAll-items");
    } catch(err) {
      console.log(err);
    }
  }

const {
  values,
  changeHandler,
  submitHandler
} = useForm(item,updateHandler);


  return (
    <section>
      <h2>Edit Item</h2>
      <form onSubmit={submitHandler}>
        <label style={{color: "darkblue"}}>Name:</label>
        <input
          type="text"
          name="name"
          value={values.name}
          onChange={changeHandler}
        />

        <label style={{color: "darkblue"}}>Price:</label>
        <input
          type="number"
          name="price"
          value={values.price}
          onChange={changeHandler}
        />

        <label style={{color: "darkblue"}}>Category: {values.category}</label>
        <select
          name="categoryId"
          value={values.category}
          onChange={changeHandler}
        >
          <option value="">-- Select Category --</option>
          {categories.map((cat) => (
            <option key={cat.id} value={cat.id}>
              {cat.category}
            </option>
          ))}
        </select>

        <label style={{color: "darkblue"}}>Type Product:</label>
        <input
          type="text"
          name="typeProduct"
          value={values.typeProduct}
          onChange={changeHandler}
        />

        <button type="submit" style={{color: "darkblue"}}>Save Changes</button>
      </form>
    </section>
  );
}
