import { useEffect } from "react";
import { getAccessToken, decodeJWT } from "../../utils/authUtils.js";

export default function Menu() {
  
useEffect(() => {
    async function fetchUser() {
      const token = await getAccessToken();
      if (token) {
        const decoded = decodeJWT(token); 
      } 
    }
    fetchUser();
  }, []);
   return (
       <>
           <h1> decoder </h1>
       
       </>
  );
}