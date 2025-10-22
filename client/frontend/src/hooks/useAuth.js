
import { login, register } from "../api/auth-api"


export const useLogin = () => {

    const loginHandler = async (username, password) => {
        
          const { password: _, ...authState} =   await login(username, password);
          
          return authState;     
    }

    return loginHandler;
}


export const useRegister = () => {
     
        const registerHandler = async (username, password,confirmPassword) => {
            const {password: _, ...authData} = await register(username, password,confirmPassword);
            
            return authData;
        };


        return registerHandler;
}


