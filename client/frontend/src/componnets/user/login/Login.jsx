import { useNavigate } from "react-router-dom";
import { useLogin } from "../../../hooks/useAuth";
import { useForm } from "../../../hooks/useForm";
import { useAuth } from "../../../context/AuthContext";
import { useState } from "react";

const initialValues = {
  username: "",
  password: "",
};

export default function Login() {
  const [error, setError] = useState("");
  const login = useLogin();
  const { loginToken } = useAuth();
  const navigate = useNavigate();

  const loginHandler = async ({ username, password }) => {
    const res = await login(username, password);

    if (res?.token) {
      await loginToken(res.token);
      navigate("/");
    } else {
      setError(res?.message || "Грешно потребителско име или парола");
    }
  };

  const { values, changeHandler, submitHandler } = useForm(
    initialValues,
    loginHandler
  );

  return (
    <section className="login-page">
      <form className="login-card" onSubmit={submitHandler}>
        <div className="login-logo">🍽️</div>

        <h1 className="login-title">Restaurant POS</h1>
        <p className="login-subtitle">Вход за персонал</p>

        <div className="form-group">
          <label>Потребител</label>
          <input
            type="text"
            name="username"
            value={values.username}
            onChange={changeHandler}
            placeholder="Въведи потребител"
            autoFocus
          />
        </div>

        <div className="form-group">
          <label>Парола</label>
          <input
            type="password"
            name="password"
            value={values.password}
            onChange={changeHandler}
            placeholder="Въведи парола"
          />
        </div>

        {error && <div className="login-error">{error}</div>}

        <button className="login-btn" type="submit">
          Вход
        </button>
      </form>
    </section>
  );
}
