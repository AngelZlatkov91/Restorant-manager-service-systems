import { useState } from "react";
import { useLogin } from "../hooks/useAuth";
import { useNavigate } from "react-router-dom";

export default function LoginAuthWindow() {
  const navigate = useNavigate();
  const [hasError, setHasError] = useState('');
  const [password, setPassword] = useState("");
  const login = useLogin();
  
  
  const handleLogin = async (e) => {
   const result = await login(password);
   if (result) {
    navigate("/tables");
    window.electronAPI.loginSuccess(result); 
   }
   setHasError(result);
  
  };

  return (
    <div style={{ padding: 40 }}>
      <h2>Вход</h2>
      <form onSubmit={handleLogin}>
        <input
          type="password"
          placeholder="Въведи парола"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        
       
        <button type="submit">Вход</button>
      </form>
    </div>
  );
}
