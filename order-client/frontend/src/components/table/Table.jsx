import { useEffect, useState } from "react";
import { getAccessToken } from "../../utils/authUtils";

export default function Table() {

     const [name, setName] = useState('');
    
      useEffect(() => {
        (async () => {
          const token = await getAccessToken();
          setName(token);
        })();
      }, []);
   return (
     <>
     <h1><strong>Personal:  {name}</strong></h1>
     
     </>
  );
}