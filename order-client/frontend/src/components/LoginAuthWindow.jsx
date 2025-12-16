import { useState } from "react";
import { useLogin } from "../hooks/useAuth";

export default function LoginAuthWindow() {
  const [hasError, setHasError] = useState('');
  const [password, setPassword] = useState("");
  const login = useLogin();
  
  
const handleLogin = async (e) => {
  e.preventDefault();
  const result = await login(password); 
  if (result.name) {
    window.electronAPI.loginSuccess(result.name);
  } else {
    setHasError("Грешна парола");
  }
};

  return (
    <div
  style={{
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    padding: 40,
    minHeight: "100vh",
    backgroundColor: "#f5f5f5",
    fontFamily: "Arial, sans-serif",
  }}
>
  <h2 style={{ marginBottom: 20, color: "#333" }}>Вход</h2>

  <form
    onSubmit={handleLogin}
    style={{
      display: "flex",
      flexDirection: "column",
      width: 300,
      gap: 15,
      backgroundColor: "#fff",
      padding: 30,
      borderRadius: 10,
      boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
    }}
  >
    <input
      type="password"
      placeholder="Въведи парола"
      value={password}
      onChange={(e) => setPassword(e.target.value)}
      style={{
        padding: "10px",
        borderRadius: 5,
        border: "1px solid #ccc",
        fontSize: 16,
      }}
    />

    {hasError && (
      <p style={{ color: "red", fontSize: 14, margin: 0 }}>{hasError}</p>
    )}

    <button
      type="submit"
      style={{
        padding: "10px",
        borderRadius: 5,
        border: "none",
        backgroundColor: "#007bff",
        color: "#fff",
        fontWeight: "bold",
        fontSize: 16,
        cursor: "pointer",
        transition: "background-color 0.2s",
      }}
      onMouseEnter={(e) => (e.target.style.backgroundColor = "#0056b3")}
      onMouseLeave={(e) => (e.target.style.backgroundColor = "#007bff")}
    >
      Вход
    </button>
  </form>
</div>
  );
}
