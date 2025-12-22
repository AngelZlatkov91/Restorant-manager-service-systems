
import { useNavigate, Link } from "react-router-dom";
import { useCreateCategory } from "../../../hooks/useCategory";
import { useForm } from "../../../hooks/useForm";
import { useState } from "react";
const initialValues = {
  categoryName: '',

}
export default function CreateCategory() {
   const navigate = useNavigate();
    const createCategory = useCreateCategory()
    const [hasError, setHasError] = useState('');

    const createHandler = async (values) => {    
          const result = await createCategory(values);
          console.log(result);  
          if (result === "Created") {
          navigate('/getAllCategory')
       } 
    };

    const {
        values,
        changeHandler,
        submitHandler,
    } = useForm(initialValues,createHandler);
   return (
    <section className="create-wrapper">
  <div className="top-nav">
    <Link to="/getAllCategory" className="secondary-link">
      ← Всички категории
    </Link>
  </div>

  <form className="create-card" onSubmit={submitHandler}>
    <h1 className="create-title">📂 Създай категория</h1>

    <div className="input-group">
      <label htmlFor="categoryName">Име на категория</label>
      <input
        type="text"
        id="categoryName"
        name="categoryName"
        value={values.categoryName}
        onChange={changeHandler}
        placeholder="Напр. Напитки"
      />
    </div>

    {hasError && (
      <div className="error-box">
        {hasError.status}
      </div>
    )}

    <button className="primary-btn" type="submit">
      Създай категория
    </button>
  </form>
     </section>


  
  );
}