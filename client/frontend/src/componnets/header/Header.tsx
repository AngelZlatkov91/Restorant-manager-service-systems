import { Link } from "react-router-dom";

export default function Header() {

    return (
        <>
           
           <header>
            <h1><Link className="home" to="/">Restorant Manager</Link></h1>
              
              <nav>
                   <Link to="/games">Menu</Link>
                   <div id="user">
                       <Link to="/inventory">Inventory</Link>
                       <Link to="/orders">Orders</Link>
                       <Link to="/reports">Reports</Link>
                       <Link to="/logout">Logout</Link>
                   </div>
                   
                   
                    <div id="guest">
                        <Link to="/login">Login</Link>
                        <Link to="/register">Register</Link>
                     </div>
                     
              </nav>
        </header>
        
        </>
    )
}