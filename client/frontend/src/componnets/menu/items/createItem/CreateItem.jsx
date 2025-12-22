import { useGetAllCategory } from "../../../../hooks/useCategory";
import { useCreateMenuItem } from "../../../../hooks/useItem";
import { useForm } from "../../../../hooks/useForm";
import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

const initialValues = {
  name: "",
  price: "",
  category: "",
  typeProduct: "",
};

export default function CreateItem() {
  const navigate = useNavigate();
  const [successMsg, setSuccessMsg] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const optionTypeProduct = [
    { value: "BAR", label: "BAR" },
    { value: "KITCHEN", label: "KITCHEN" },
  ];

  const [categories, refreshCategories] = useGetAllCategory();
  const createMenuItem = useCreateMenuItem();

  const createHandler = async (values) => {
    if (!values.name || !values.price || !values.category || !values.typeProduct) {
      setErrorMsg("Моля попълнете всички полета!");
      return;
    }

    try {
      const res = await createMenuItem(values);
      if (res === "Created") {
        setSuccessMsg("Артикулът е създаден успешно!");
        setErrorMsg("");
        refreshCategories(); // 🔄 обновяване на категориите
        setTimeout(() => navigate("/getAll-items"), 1200);
      } else {
        setErrorMsg("Грешка при създаване на артикул!");
      }
    } catch (err) {
      setErrorMsg("Сървърна грешка!");
    }
  };

  const { values, changeHandler, submitHandler } = useForm(
    initialValues,
    createHandler
  );

  return (
    <section className="create-page">
      <Link to="/getAll-items" className="back-link">← Назад към всички артикули</Link>
      <form className="create-form" onSubmit={submitHandler}>
        <h1>➕ Създай артикул</h1>

        {successMsg && <div className="success-box">{successMsg}</div>}
        {errorMsg && <div className="error-box">{errorMsg}</div>}

        <label htmlFor="name">Име на артикул</label>
        <input
          type="text"
          id="name"
          name="name"
          value={values.name}
          onChange={changeHandler}
          placeholder="Въведи име..."
        />

        <label htmlFor="price">Цена</label>
        <input
          type="number"
          id="price"
          name="price"
          value={values.price}
          onChange={changeHandler}
          placeholder="Въведи цена..."
        />

        <label htmlFor="category">Категория</label>
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

        <label htmlFor="typeProduct">Тип продукт</label>
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

        <button className="btn btn-primary" type="submit">
          Създай
        </button>
      </form>
    </section>
  );
}
