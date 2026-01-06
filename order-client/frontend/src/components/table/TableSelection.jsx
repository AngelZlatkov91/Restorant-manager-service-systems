import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useGetAllTable } from "../../hooks/useTable";

export default function TableSelection() {
  const navigate = useNavigate();
  const location = useLocation();

  const [loggedUser, setLoggedUser] = useState("");
  const [tables, fetchTables] = useGetAllTable();

  const loadTables = async () => {
    await fetchTables();
  };

  useEffect(() => {
    (async () => {
      const token = await window.electronAPI.getToken();
      setLoggedUser(token?.name || "Гост");
      await loadTables();
    })();
  }, []);

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

  const getTableClass = (table) => {
    if (table.empty) return "free";
    if (table.owner === loggedUser) return "busy-mine";
    return "busy-other";
  };

  return (
    <div className="wrapper">
      <header className="header">
        <h1 className="welcome">Добре дошъл, {loggedUser}</h1>
        <button className="logout-btn" onClick={handleLogout}>
          Изход
        </button>
      </header>

      <section className="section">
        {tables && tables.length > 0 ? (
          <ul className="table-list">
            {tables.map((table) => (
              <li key={table.id}>
                <button
                  className={`table-btn ${getTableClass(table)}`}
                  onClick={() => tableEventHandler(table.id)}
                >
                  <div className="table-name">{table.tableName}</div>

                  {!table.empty && (
                    <div className="table-status">
                      Заета от: {table.owner}
                    </div>
                  )}
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
