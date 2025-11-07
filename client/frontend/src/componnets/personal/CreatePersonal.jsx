import { useState } from "react";
import { useCreatePersonal } from "../../hooks/usePersonal";
import { useNavigate } from "react-router-dom";
import { useForm } from "../../hooks/useForm";


const initialValues = {
  name: '',
  password: '',
}
export default function CreatePersonal() {
const [error, setError] = useState('');
const addPersonal = useCreatePersonal();
const navigate = useNavigate();

const createHandler = async (values) => {
   const result = await addPersonal(values);
   console.log(result);

};

const {
  values,
  changeHandler,
  submitHandler
} = useForm(initialValues,createHandler);

   return (
          <>
           <section id="register-page" className="content auth">
            <form id="register" onSubmit={submitHandler}>
                <div className="container">
                    <div className="brand-logo"></div>
                    <h1>Create Personal</h1>

                    <label htmlFor="username">Name:</label>
                    <input 
                    type="text" 
                    id="name" 
                    name="name" 
                    value={values.name}
                    
                    onChange={changeHandler}
                    />

                    <label htmlFor="password">Password:</label>
                    <input 
                    type="text" 
                    name="password" 
                    id="password"
                    value={values.password}
                    onChange={changeHandler}
                    />

                    {error && (
                        <p>
                           <span style={{fontSize: '20px', color: 'red'}}>{error}</span>
                         </p>
                    )}
                    

                    <input className="btn submit" type="submit" value="Create Personal" />
                </div>
            </form>
          </section>
          
          </>
  );
}