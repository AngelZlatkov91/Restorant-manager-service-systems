import { useNavigate } from "react-router-dom";
import { useDeleteTable, useGetAllTable } from "../../hooks/useTable";
import { useState, useEffect } from "react";
import ConfirmPopup from "../confirmModal/ConfirmPop";

export default function Table() {
  const navigate = useNavigate();
  const [tables, fetchTables] = useGetAllTable();
  
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [tableToDelete, setItemToDelete] = useState(null);
  const [popupPos, setPopupPos] = useState({ top: 0, left: 0 });

  useEffect(() => {
    fetchTables();
  }, []);

  const handleDeleteClick = (e, item) => {
    const rect = e.target.getBoundingClientRect();
    setPopupPos({ top: rect.top + window.scrollY + 30, left: rect.left + rect.width / 2 });
    setItemToDelete(item);
    setIsConfirmOpen(true);
  };

  const handleConfirm = async () => {
    if (!tableToDelete) return;
    await useDeleteTable(tableToDelete.id);
    fetchTables();
    setIsConfirmOpen(false);
    setItemToDelete(null);
  };

  const handleCancel = () => {
    setIsConfirmOpen(false);
    setItemToDelete(null);
  };

  const handleEdit = (id) => {
    navigate(`/editTable/${id}`);
  };
  const createTableHandler = () => {
    navigate('/createTable');
  }

  return (
    <section id="tables" style={{ padding: "20px" }}>
      <h2>Tables</h2>
      <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={createTableHandler} style={{
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "5px",
                  background: "#4caf50",
                  color: "white",
                  cursor: "pointer",
                }}>Create Table</button>
      </div>
      {tables && tables.length > 0 ? (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {tables.map((table) => (
            <li key={table.id} style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
              background: "#f9f9f9",
              marginBottom: "10px",
              padding: "10px 15px",
              borderRadius: "8px",
              boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
            }}>
              <strong>{table.tableName}</strong>

              <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={() => handleEdit(table.id)} style={{
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "5px",
                  background: "#4caf50",
                  color: "white",
                  cursor: "pointer",
                }}>Edit</button>

                <button onClick={(e) => handleDeleteClick(e, table)} style={{
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "5px",
                  background: "#f44336",
                  color: "white",
                  cursor: "pointer",
                }}>Delete</button>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p>Няма налични артикули.</p>
      )}

      <ConfirmPopup
        isOpen={isConfirmOpen}
        message={`Сигурен ли си, че искаш да изтриеш "${tableToDelete?.tableName}"?`}
        onConfirm={handleConfirm}
        onCancel={handleCancel}
        position={popupPos}
      />
    </section>
  );
}
