
import { login, register } from "../api/auth-api"


export const useLogin = () => {

    const loginHandler = async (username, password) => {
        
          const authState =   await login(username, password);
          
          return authState;     
    }

    return loginHandler;
}


export const useRegister = () => {
     
        const registerHandler = async (username, password,confirmPassword) => {
            const authData = await register(username, password,confirmPassword);
            return authData;
        };

        return registerHandler;
}


