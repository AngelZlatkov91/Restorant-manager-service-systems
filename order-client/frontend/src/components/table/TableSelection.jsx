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
  <h2 style={{ marginBottom: "20px" }}>Tables: {name}</h2>

  {tables && tables.length > 0 ? (
    <ul style={{ listStyle: "none", padding: 0, display: "flex", flexWrap: "wrap", gap: "15px" }}>
      {tables.map((table) => (
        <li key={table.id}>
          <button
            className={`table-status ${table.empty ? "free" : "busy"}`}
            onClick={() => tableEventHandler(table.id)}
            style={{
              minWidth: "120px",
              padding: "16px",
              borderRadius: "12px",
              border: "none",
              fontSize: "18px",
              fontWeight: "600",
              color: "#fff",
              cursor: "pointer",
              background: table.empty ? "#4caf50" : "#f44336",
              boxShadow: "0 4px 10px rgba(0,0,0,0.15)",
              transition: "transform 0.2s, box-shadow 0.2s",
            }}
            onMouseEnter={(e) => {
              e.currentTarget.style.transform = "scale(1.05)";
              e.currentTarget.style.boxShadow = "0 6px 14px rgba(0,0,0,0.2)";
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.transform = "scale(1)";
              e.currentTarget.style.boxShadow = "0 4px 10px rgba(0,0,0,0.15)";
            }}
          >
            {table.tableName}
          </button>
        </li>
      ))}
    </ul>
  ) : (
    <p>Няма налични маси.</p>
  )}
</section>
  );
}
