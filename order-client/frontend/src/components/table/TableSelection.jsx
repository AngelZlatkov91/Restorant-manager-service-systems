import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getAccessToken } from "../../utils/authUtils";
import { useGetAllTable } from "../../hooks/useTable";

export default function TableSelection() {
  const navigate = useNavigate();
  const [name, setName] = useState('');

  const [tables, fetchTables] = useGetAllTable();
  useEffect(() => {
    (async () => {
      const token = await getAccessToken();
      setName(token);
    })();
  }, []);

  const tableEventHandler = (id) => { 
      navigate(`/orderClient/${id}`);
  };



  return (
    <section id="tables" style={{ padding: "20px" }}>
      <h2>Tables: {name}</h2>

      {tables && tables.length > 0 ? (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {tables.map((table) => (
            <li key={table.id} style={{
              display: "flex",
              justifyContent: "space-between",
              textAlign: "center",
              alignItems: "center",
              background: "#ffffff",
              marginBottom: "10px",
              padding: "14px 18px",
              borderRadius: "10px",
              boxShadow: "0 2px 6px rgba(0,0,0,0.15)",
            }}>

              <button className={`table-status ${table.empty ? "free" : "busy"}`}
                style={{ fontSize: "18px" }} onClick={() => tableEventHandler(table.id)}> {table.tableName}
              </button>

            </li>
          ))}
        </ul>
      ) : (
        <p>Няма налични артикули.</p>
      )}

    </section>
  );
}
