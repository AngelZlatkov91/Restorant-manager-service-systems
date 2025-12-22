import { useParams, useNavigate } from "react-router-dom";
import { useGetAllCategory } from "../../../../hooks/useCategory";
import { useGetItemById, useUpdateItem } from "../../../../hooks/useItem";
import { useForm } from "../../../../hooks/useForm";

export default function EditItem() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [categories] = useGetAllCategory();
  const [item] = useGetItemById(id);

  const updateHandler = async (values) => {
    try {
      await useUpdateItem(values);
      navigate("/getAll-items");
    } catch (err) {
      console.log(err);
    }
  };

  const { values, changeHandler, submitHandler } = useForm(item, updateHandler);

  return (
    <section className="edit-page">
      <div className="edit-card">
        <h2>Edit Item</h2>
        <form onSubmit={submitHandler}>
          <div className="form-group">
            <label>Name:</label>
            <input
              type="text"
              name="name"
              value={values.name}
              onChange={changeHandler}
              placeholder="Enter item name"
            />
          </div>

          <div className="form-group">
            <label>Price:</label>
            <input
              type="number"
              name="price"
              value={values.price}
              onChange={changeHandler}
              placeholder="Enter price"
            />
          </div>

          <div className="form-group">
            <label>Category:</label>
            <select
              name="category"
              value={values.category}
              onChange={changeHandler}
            >
              <option value="">-- Select Category --</option>
              {categories.map((cat) => (
                <option key={cat.id} value={cat.category}>
                  {cat.category}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Type Product:</label>
            <select
              name="typeProduct"
              value={values.typeProduct}
              onChange={changeHandler}
            >
              <option value="">-- Select Type --</option>
              <option value="BAR">BAR</option>
              <option value="KITCHEN">KITCHEN</option>
            </select>
          </div>

          <button className="btn btn-primary" type="submit">
            💾 Save Changes
          </button>
        </form>
      </div>
    </section>
  );
}
