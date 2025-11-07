import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import { useEffect, useState } from "react";

export default function Header() {
  const {isAuth, logout} = useAuth();

  return (
    <header>
      <h1>
        <Link className="home" to="/">Restaurant Manager</Link>
      </h1>

      <nav className="nav">
        {isAuth ? (
          <>
            <div className="dropdown">
              <button className="dropbtn">Menu</button>
              <div className="dropdown-content">
                <Link to="/getAllCategory">Get All Categories</Link>
                <Link to="/getAll-items">Get All Items</Link>
              </div>
            </div>

            <div>
              <Link to="/table">Table</Link>
              <Link to="/personal">Personal</Link>
              <Link to="/inventory">Inventory</Link>
              <Link to="/orders">Orders</Link>
              <Link to="/reports">Reports</Link>
              <button onClick={logout} className="btn-link"
              style={{color: "blue"}}>
                Logout
              </button>
            </div>
          </>
        ) : (
          <div id="guest">
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </div>
        )}
      </nav>
    </header>
  );
}
