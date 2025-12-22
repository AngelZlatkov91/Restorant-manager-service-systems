import { Link } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

export default function Header() {
  const { isAuth, logout } = useAuth();

  return (
    <header className="app-header">
      <div className="header-left">
        <h1 className="logo">🍽 Restaurant Manager</h1>
      </div>

      <nav className="header-nav">
        {isAuth ? (
          <>
            <div className="menu-group">
              <Link to="/table">Маси</Link>
              <Link to="/orders">Поръчки</Link>
              <Link to="/inventory">Инвентар</Link>
              <Link to="/reports">Отчети</Link>
            </div>

            <div className="menu-group">
              <Link to="/getAllCategory">Категории</Link>
              <Link to="/getAll-items">Артикули</Link>
            </div>

            <button className="logout-btn" onClick={logout}>
              ⏻ Изход
            </button>
          </>
        ) : (
          <div className="menu-group">
            <Link to="/login">Вход</Link>
            <Link to="/register">Регистрация</Link>
          </div>
        )}
      </nav>
    </header>
  );
}
