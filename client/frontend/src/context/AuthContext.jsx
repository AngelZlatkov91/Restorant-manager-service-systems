 import { createContext, useContext, useState, useEffect } from "react";
import { getAccessToken } from "../utils/authUtils";
import { useNavigate } from "react-router-dom";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const navigate = useNavigate();
  const [isAuth, setIsAuth] = useState(false);

  useEffect(() => {
    (async () => {
      const token = await getAccessToken();
      setIsAuth(!!token);
    })();
  }, []);

  const loginToken = async (token) => {
    await window.electronAPI.saveToken(token);
    setIsAuth(true); 
  };

  const logout = async () => {

    await window.electronAPI.saveToken(null);
    navigate("/");
    setIsAuth(false);
  };

  return (
    <AuthContext.Provider value={{ isAuth, loginToken, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}

