import { Link } from "react-router-dom";


export default function Header() {

  return (
    <header>
      <h1>
        <Link className="home" to="/">Restaurant Manager</Link>
      </h1>
      <nav className="nav">
       
        <div className="dropdown">
          <button className="dropbtn">Menu</button>
          <div className="dropdown-content">
            <Link to="/createCategory">Create Category</Link>
            <Link to="/getAllCategory">Get All Categories</Link>
            <Link to="/createItem">Create Item</Link>
            <Link to="/getAll-items">Get All Items</Link>
          </div>
        </div>
        <div id="user">
          <Link to="/inventory">Inventory</Link>
          <Link to="/reports">Reports</Link>
          <Link to="/logout">Logout</Link>
        </div>
        
        
           <div id="guest">
            <Link to="/login">Login</Link>
             <Link to="/register">Register</Link>
          </div>
       
        
      </nav>
    </header>
  );
}
