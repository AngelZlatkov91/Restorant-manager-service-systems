import { useContext } from "react";
import { Link } from "react-router-dom";


export default function Header() {
   

   return (
      <>
        <header>
            
            <h1><Link className="home" to="/">Restorant Manager</Link></h1>
            <nav>
                <Link to="/menu">Menu</Link>
               
                   <div id="user">
                       <Link to="/inventory">Inventory</Link>
                       <Link tp="/reports">Reports</Link>
                       <Link to="/logout">Logout</Link>
                   </div>
                   
                    <div id="guest">
                        <Link to="/login">Login</Link>
                        <Link to="/register">Register</Link>
                     </div>
            </nav>
        </header>
      </>
  );
}