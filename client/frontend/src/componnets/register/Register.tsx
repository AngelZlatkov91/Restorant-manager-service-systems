import { Link, useNavigate } from "react-router-dom";
// import { useRegister } from "../../hooks/useAuth";
// import { useForm } from "../../hooks/useForm";
import { useState } from "react";
const initialValues = {email: '', password: '', 'confirm-password': ''}
export default function Register() {
    const [error, setError] = useState('');
    // const register = useRegister();
    const navigate = useNavigate();

    // const registerHandler = async (values) => {
    //    if (values.password !== values['confirm-password']) {
    //    return setError('Password missmatch!');
             
    //    }

    //        try {
    //         await register(values.email,values.password);
    //         navigate('/');
    //        } catch(err) {
    //         setError(err.message)
            
    //        }
    // }

    // const {
    //     values,
    //     changeHandler,
    //     submitHandler
    // } = useForm(initialValues,registerHandler);

   return (
       <>
          <section id="register-page" className="content auth">
            <form id="register" 
            // onSubmit={submitHandler}
            >
                <div className="container">
                    
                    <h1>Register</h1>

                    <label htmlFor="username">Username:</label>
                    <input 
                    type="text" 
                    id="username" 
                    name="username" 
                    // value={values.email}
                    placeholder="maria@email.com" 
                    // onChange={changeHandler}
                    />

                    <label htmlFor="pass">Password:</label>
                    <input 
                    type="password" 
                    name="password" 
                    id="register-password"
                    // value={values.password}
                    // onChange={changeHandler}
                    />

                    <label htmlFor="con-pass">Confirm Password:</label>
                    <input 
                    type="password" 
                    name="confirm-password"
                    // value={values['confirm-password']} 
                    id="confirm-password" 
                    // onChange={changeHandler}
                    />
                    {/* {error && (
                        <p>
                           <span style={{fontSize: '20px', color: 'red'}}>{error}</span>
                         </p>
                    )} */}
                    

                    <input className="btn submit" type="submit" value="Register" />

                    <p className="field">
                        <span>If you already have profile click <Link to="/login">Login</Link></span>
                    </p>
                </div>
            </form>
          </section>
       </>
  );
}