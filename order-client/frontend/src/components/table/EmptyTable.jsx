import { useEffect, useState } from "react";
import { getAccessToken } from "../../utils/authUtils";
import { useNavigate, useParams } from "react-router-dom";
import { useGetAllMenuItems } from "../../hooks/useItem";

export default function EmptyTable() {
  const navigate = useNavigate();
  const {id} = useParams();

  const [items, fetchItems] = useGetAllMenuItems();
  console.log(items);
     const [name, setName] = useState('');
    
      useEffect(() => {
        (async () => {
          const token = await getAccessToken();
          setName(token);
        })();
      }, []);

 const getAllTable = () => {
     navigate('/tables');
 }
      
   return (
     <>
     <h1><strong>Table number </strong></h1>

     <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={getAllTable} style={{
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "5px",
                  background: "#4caf50",
                  color: "white",
                  cursor: "pointer",
                }}>ALL TABLES</button>
              </div>
     
     </>
  );
}