import {useNavigate } from "react-router-dom"
import { useLogin } from "../../../hooks/useAuth";
import { useForm } from "../../../hooks/useForm"
import { useAuth } from "../../../context/AuthContext";

const initialValues = {
  username: '', 
  password: '',
};
export default function Login() {
   const login = useLogin();
   const {loginToken} = useAuth();
     const navigate = useNavigate()
   const loginHandler = async ({username, password}) => {
          try {
                const res =   await login(username,password);
                 await loginToken(res.token);
                navigate('/');
            } catch (err) {
              console.log(err);
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
                    
                    <input type="submit" className="btn submit" value="Login" />
                    <p className="field">
                        <span>If you don't have profile click <a href="#">here</a></span>
                    </p>
                </div>
            </form>
        </section>
       </>
  )
}