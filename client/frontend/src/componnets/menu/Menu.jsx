import { NavLink } from "react-router-dom";


export default function Menu() {
  return (
    <nav className="sidebar">
      <h2>Categories</h2>
      <div className="menu-section">
        <NavLink to="/createCategory" className="menu-link">
          Create Category
        </NavLink>
        <NavLink to="/getAllCategory" className="menu-link">
          Get All Categories
        </NavLink>
      </div>

      <h2>Menu Items</h2>
      <div className="menu-section">
        <NavLink to="/createItem" className="menu-link">
          Create Item
        </NavLink>
        <NavLink to="/getAll-items" className="menu-link">
          Get All Items
        </NavLink>
      </div>
    </nav>
  );
}
