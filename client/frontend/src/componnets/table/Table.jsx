import { useNavigate } from "react-router-dom";
import { useDeleteTable, useGetAllTable } from "../../hooks/useTable";
import { useState, useEffect } from "react";
import ConfirmPopup from "../confirmModal/ConfirmPop";

export default function Table() {
  const navigate = useNavigate();
  const [tables, fetchTables] = useGetAllTable();
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [tableToDelete, setTableToDelete] = useState(null);

  useEffect(() => {
    fetchTables();
  }, []);

  const handleDeleteClick = (table) => {
    setTableToDelete(table);
    setIsConfirmOpen(true);
  };

  const handleConfirm = async () => {
    if (!tableToDelete) return;
    await useDeleteTable(tableToDelete.id);
    await fetchTables();
    setIsConfirmOpen(false);
    setTableToDelete(null);
  };

  const handleCancel = () => {
    setIsConfirmOpen(false);
    setTableToDelete(null);
  };

  return (
    <section className="tables-page">
      <header className="tables-header">
        <h2>Маси</h2>
        <button className="btn btn-primary" onClick={() => navigate("/createTable")}>
          ➕ Създай маса
        </button>
      </header>

      {tables && tables.length > 0 ? (
        <ul className="tables-list">
          {tables.map((table) => (
            <li key={table.id} className="table-card">
              <div className="table-info">
                <span className={`table-dot ${table.empty ? "free" : "busy"}`} />
                <strong>{table.tableName}</strong>
              </div>

              <div className="table-actions">
                <button
                  className="btn btn-success"
                  onClick={() => navigate(`/editTable/${table.id}`)}
                >
                  ✏️ Редакция
                </button>

                <button
                  className="btn btn-danger"
                  onClick={() => handleDeleteClick(table)}
                >
                  🗑 Изтрий
                </button>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className="empty-state">Няма налични маси.</p>
      )}

      <ConfirmPopup
        isOpen={isConfirmOpen}
        message={`Сигурен ли си, че искаш да изтриеш "${tableToDelete?.tableName}"?`}
        onConfirm={handleConfirm}
        onCancel={handleCancel}
      />
    </section>
  );
}
