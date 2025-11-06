import { useState } from "react";
import { useCreateTable } from "../../hooks/useTable";
import { useForm } from "../../hooks/useForm";
const initialValues = {
  tableName: ''
}
export default function CreateTable() {
   const createTable = useCreateTable();
   const [hasError, setHasError] = useState('');

   const createHandler = async (values) => {
   
      const result =  await createTable(values);
      
     if (result.resMessage) {
            setHasError(result.resMessage);
     } 
   }

   const {
    values,
    changeHandler,
    submitHandler
   } = useForm(initialValues,createHandler);

    
   return (
       <>
         <section id="create-page" className="auth">
            <form id="create" onSubmit={submitHandler}>
                <div className="container">

                    <h1>Create Table</h1>
                    <label htmlFor="categoryName">Category Name:</label>
                    <input 
                    type="text" 
                    id="tableName" 
                    name="tableName" 
                    value={values.tableName}
                    onChange={changeHandler}
                    placeholder="Enter table name..." />
                   <input className="btn submit" type="submit" value="Create table" />
                   {hasError && (
                        <p>
                           <span style={{fontSize: '20px', color: 'red'}}>{hasError}</span>
                         </p>
                    )}
                </div>
            </form>
         </section>
       </>
  );
}