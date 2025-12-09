import {useNavigate } from "react-router-dom"
import { useLogin } from "../../../hooks/useAuth";
import { useForm } from "../../../hooks/useForm"
import { useAuth } from "../../../context/AuthContext";
import { useState } from "react";

const initialValues = {
  username: '', 
  password: '',
};
export default function Login() {
  const [error, setError] = useState('');
   const login = useLogin();
   const {loginToken} = useAuth();
     const navigate = useNavigate()
   const loginHandler = async ({username, password}) => {
                const res =  await login(username,password);
                if (res.token) {
                  await loginToken(res.token);
                  navigate('/');
                } else {
                  console.log(res);
                  alert('Login failed');
                  setError(res);
                }
   }
    const {values, changeHandler, submitHandler } = useForm(
        initialValues,
        loginHandler
    );
    
    

   return (
       <>
          <section id="login-page" className="auth">
            <form id="login" onSubmit={submitHandler}>

                <div className="container">
                    <div className="brand-logo"></div>
                    <h1>Login</h1>
                    
                    <label htmlFor="username">Username:</label>
                    <input 
                    type="text" 
                    id="username" 
                    name="username"
                    value={values.username}
                    onChange={changeHandler} 
                    
                    />

                    <label htmlFor="login-pass">Password:</label>
                    <input 
                    type="password" 
                    id="login-password" 
                    name="password"
                    value={values.password}
                    onChange={changeHandler}  
                    />
                    {error && (
                        <p>
                           <span style={{fontSize: '20px', color: 'red'}}>{error}</span>
                         </p>
                    )}
                    
                    <input type="submit" className="btn submit" value="Login" />
                   
                </div>
            </form>
        </section>
       </>
  )
}