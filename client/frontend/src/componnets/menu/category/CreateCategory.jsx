
import { useCreateCategory } from "../../../hooks/useCategory";
import { useForm } from "../../../hooks/useForm";
import { useState } from "react";
const initialValues = {
  categoryName: '',

}
export default function CreateCategory() {
   
    const createCategory = useCreateCategory()
    const [hasError, setHasError] = useState('');

    const createHandler = async (values) => {
        try {
             await createCategory(values);
             
        } catch (err) {
            setHasError(err.message);
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
                   {hasError && (
                        <p>
                           <span style={{fontSize: '20px', color: 'red'}}>{hasError.status}</span>
                         </p>
                    )}
                </div>
            </form>
         </section>
      
    </>
  
  );
}