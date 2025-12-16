import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useGetAllTable } from "../../hooks/useTable";

export default function TableSelection() {
  const navigate = useNavigate();
  const location = useLocation();
  const [name, setName] = useState("");
  const [tables, fetchTables] = useGetAllTable();

  // Функция за зареждане на масите
  const loadTables = async () => {
    await fetchTables();
  };

  useEffect(() => {
    (async () => {
      const token = await window.electronAPI.getToken();
      setName(token || "Гост");
      await loadTables();
    })();
  }, []);

  // Автоматично обновяване при връщане от OrderClient
  useEffect(() => {
    if (location.state?.refresh) {
      loadTables();
    }
  }, [location.state]);

  const tableEventHandler = (id) => {
    navigate(`/orderClient/${id}`);
  };

  const handleLogout = () => {
    window.electronAPI.logout(); 
  };

  return (
    <div className="wrapper">
      <header className="header">
        <h1 className="welcome">Добре дошъл, {name}</h1>
        <button className="logout-btn" onClick={handleLogout}>Изход</button>
      </header>

      <section className="section">
        {tables && tables.length > 0 ? (
          <ul className="table-list">
            {tables.map((table) => (
              <li key={table.id}>
                <button
                  className={`table-btn ${table.empty ? "free" : "busy"}`}
                  onClick={() => tableEventHandler(table.id)}
                >
                  <div className="table-name">{table.tableName}</div>
                  {!table.empty && <div className="table-status">Заета</div>}
                </button>
              </li>
            ))}
          </ul>
        ) : (
          <p className="no-tables">Няма налични маси.</p>
        )}
      </section>
    </div>
  );
}
