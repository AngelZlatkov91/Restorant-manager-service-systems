import { useGetAllCategory } from "../../../../hooks/useCategory";
import { useCreateMenuItem } from "../../../../hooks/useItem";
import { useForm } from "../../../../hooks/useForm";
import { useNavigate } from "react-router-dom";

const initialValues = {
  name: "",
  price: "",
  category: "",
  typeProduct: "",
};

export default function CreateItem() {
  const navigate = useNavigate();
  const optionTypeProduct = [
    { value: "BAR", label: "BAR" },
    { value: "KITCHEN", label: "KITCHEN" },
  ];

  const [categories, refreshCategories] = useGetAllCategory();
  const createMenuItem = useCreateMenuItem();

  const createHandler = async (values) => {
      const menuItemCreate = await createMenuItem(values);
       if (menuItemCreate === "Created") {
          navigate("/getAll-items")
       } 
  };

  const { values, changeHandler, submitHandler } = useForm(
    initialValues,
    createHandler
  );

  return (
    <section id="create-page" className="auth">
      <form id="create" onSubmit={submitHandler}>
        <div className="container">
          <h1>Create Item</h1>

          <label htmlFor="name">Item Name:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={values.name}
            onChange={changeHandler}
            placeholder="Enter item name..."
          />

          <label htmlFor="price">Price:</label>
          <input
            type="number"
            id="price"
            name="price"
            value={values.price}
            onChange={changeHandler}
            placeholder="Enter item price..."
          />

          <label htmlFor="category">Category</label>
          <select
            id="category"
            name="category"
            value={values.category}
            onChange={changeHandler}
          >
            <option value="">-- Избери категория --</option>
            {categories.map((cat) => (
              <option key={cat.id} value={cat.category}>
                {cat.category}
              </option>
            ))}
          </select>

          <label htmlFor="typeProduct">Type Product</label>
          <select
            id="typeProduct"
            name="typeProduct"
            value={values.typeProduct}
            onChange={changeHandler}
          >
            <option value="">-- Избери тип продукт --</option>
            {optionTypeProduct.map((type) => (
              <option key={type.value} value={type.value}>
                {type.label}
              </option>
            ))}
          </select>

          <input
            className="btn submit"
            type="submit"
            value="Create Item"
            style={{ marginTop: "10px" }}
          />
        </div>
      </form>
    </section>
  );
}
