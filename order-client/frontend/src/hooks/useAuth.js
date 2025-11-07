import { login } from "../api/auth-api";

export const useLogin = () => {

    const loginHandler = async (password) => {
        
          const result =  await login(password);
          
          return result;     
    }

    return loginHandler;
}