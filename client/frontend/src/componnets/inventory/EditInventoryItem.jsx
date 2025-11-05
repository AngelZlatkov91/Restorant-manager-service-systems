import { useNavigate, useParams } from "react-router-dom";
import { useGetItemFromInventory, useUpdateInventoryItem } from "../../hooks/useInventory";
import { useForm } from "../../hooks/useForm";

export default function EditInventoryItem() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [item, setItem] = useGetItemFromInventory(id);
  

  const updateHandler = async (values) => {
    try {
      const quantityUpdate = useUpdateInventoryItem(values);
      navigate("/inventory");
    } catch (err) {
        console.log(err.message);
    }
  };

  const {
    values,
    changeHandler,
    submitHandler
  } = useForm(item,updateHandler);


  


   return (
        <section>
      <h2>Edit Item quantity</h2>
      <form onSubmit={submitHandler}>
        <label style={{color: "darkblue"}}>Quantity:</label>
        <input
          type="number"
          name="quantity"
          value={values.quantity}
          onChange={changeHandler}
        />


        <button type="submit" style={{color: "darkblue"}}>Save Changes</button>
      </form>
    </section>
  );
}