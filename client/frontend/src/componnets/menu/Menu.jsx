import { Link, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import { getAccessToken, decodeJWT } from "../../utils/authUtils.js";

export default function Menu() {
  const navigate = useNavigate();
 
  
  useEffect(() => {
    async function fetchUser() {
      const token = await getAccessToken();
      if (!token) {
        return; 
      } 
      const decoded = decodeJWT(token);
      const isAdmin = decoded.scope;
      if (isAdmin !== 'ROLE_ADMIN') {
             navigate('/login');  
      }
    }
    fetchUser();
  }, []);

   return (
       <>
           <div className="category-items">
              <Link to="/crateCategory">Create Category</Link>
              <Link to="/getAllCategory">Get all Category</Link>
           </div>
           <div className="menu-items">
              <Link to="/createItem">Create Item</Link>
              <Link to="/getAll-items">Get all Items</Link>
           </div>
       
       </>
  );
}