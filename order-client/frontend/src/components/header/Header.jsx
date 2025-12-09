import { Link } from "react-router-dom";

export default function Header() {


   return (
    <header>
      <h1>
        <Link className="home" to="/">Orders</Link>
      </h1>
      <nav className="nav">
              <Link className="links" to="/reports">Reports</Link>
      </nav>
    </header>
  );
}