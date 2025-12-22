import { useNavigate } from "react-router-dom";
import { useRegister } from "../../../hooks/useAuth";
import { useForm } from "../../../hooks/useForm";
import { useState } from "react";

const initialValues = {
  username: "",
  password: "",
  "confirm-password": "",
};

export default function Register() {
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const register = useRegister();
  const navigate = useNavigate();

  const registerHandler = async (values) => {
    setError("");

    if (values.password !== values["confirm-password"]) {
      return setError("Паролите не съвпадат");
    }

    try {
      setLoading(true);
      const result = await register(
        values.username,
        values.password,
        values["confirm-password"]
      );

      if (result.message === "Success!") {
        navigate("/login");
      } else {
        setError(result.message || "Грешка при регистрация");
      }
    } catch (err) {
      setError("Възникна неочаквана грешка");
    } finally {
      setLoading(false);
    }
  };

  const { values, changeHandler, submitHandler } = useForm(
    initialValues,
    registerHandler
  );

  return (
    <section className="auth-page">
      <form className="auth-form" onSubmit={submitHandler}>
        <div className="auth-container">
          <div className="brand-logo" />

          <h1 className="auth-title">Регистрация</h1>

          <label>Потребителско име</label>
          <input
            type="text"
            name="username"
            value={values.username}
            onChange={changeHandler}
            required
          />

          <label>Парола</label>
          <input
            type="password"
            name="password"
            value={values.password}
            onChange={changeHandler}
            required
          />

          <label>Потвърди парола</label>
          <input
            type="password"
            name="confirm-password"
            value={values["confirm-password"]}
            onChange={changeHandler}
            required
          />

          {error && <p className="auth-error">{error}</p>}

          <button
            type="submit"
            className="auth-btn"
            disabled={loading}
          >
            {loading ? "Моля изчакайте..." : "Регистрация"}
          </button>
        </div>
      </form>
    </section>
  );
}
