import { useNavigate } from "react-router-dom";
import { useCreateCategory } from "../../../hooks/useCategory";
import { useForm } from "../../../hooks/useForm";
const initialValues = {
  categoryName: '',

}
export default function CreateCategory() {
    const navigate = useNavigate();
    const createCategory = useCreateCategory()

    const createHandler = async (values) => {
        try {
            await createCategory(values);
            
        } catch (err) {
           console.log(err);
        }
    };

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

                    <h1>Create Category</h1>
                    <label htmlFor="categoryName">Category Name:</label>
                    <input 
                    type="text" 
                    id="categoryName" 
                    name="categoryName" 
                    value={values.categoryName}
                    onChange={changeHandler}
                    placeholder="Enter categoryName..." />


                   <input className="btn submit" type="submit" value="Create Category" />
                </div>
            </form>
         </section>
      
    </>
  
  );
}