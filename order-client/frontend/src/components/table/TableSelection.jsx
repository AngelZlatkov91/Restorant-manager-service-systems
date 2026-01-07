import { useEffect, useState, useMemo, use } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useGetAllTable } from "../../hooks/useTable";
import { useGetReport, useIsCheck } from "../../hooks/useReposrt";

export default function TableSelection() {
  const navigate = useNavigate();
  const location = useLocation();
  const [loggedUser, setLoggedUser] = useState("");
  const [tables, fetchTables] = useGetAllTable();
  const [report, fetchReport] = useGetReport();
  const [showReportModal, setShowReportModal] = useState(false);
  
  useEffect(() => {
    (async () => {
      const token = await window.electronAPI.getToken();
      setLoggedUser(token?.name || "Гост");
      await fetchTables();
      await fetchReport();
    })();
  }, []);

  useEffect(() => {
    if (location.state?.refresh) {
      fetchTables();
      fetchReport();
    }
  }, [location.state]);

  const tableEventHandler = (id) => {
    navigate(`/orderClient/${id}`);
  };

  const handleLogout = () => {
    window.electronAPI.logout();
  };


  const hasActiveTables = useMemo(
    () => tables?.some((t) => !t.empty),
    [tables]
  );

  const totalTurnover = useMemo(() => {
    return report.dailyReport || 0;
  });
  const getTableClass = (table) => {
    if (table.empty) return "free";
    if (table.owner === loggedUser) return "busy-mine";
    return "busy-other";
  };

  const handleReportClick = () => {
    if (hasActiveTables) return;
    setShowReportModal(true);
  };

  const confirmReport = async() => {
    await useIsCheck(report.id);
    fetchReport();
    setShowReportModal(false);
  };

  return (
    <div className="wrapper">
      <header className="header">
        <h1 className="welcome">Добре дошъл, {loggedUser}</h1>

        <button
          className="report-btn"
          onClick={handleReportClick}
          disabled={hasActiveTables}
          title={
            hasActiveTables
              ? "Има активни маси – не може да се отчете"
              : "Отчитане на смяна"
          }
        >
          Отчитане ({totalTurnover.toFixed(2)} EUR)
        </button>

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

      
      {showReportModal && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Отчитане на смяна</h3>
            <p><strong>Персонал:</strong> {loggedUser}</p>
            <p><strong>Оборот:</strong> {totalTurnover.toFixed(2)} EUR</p>

            <div className="modal-actions">
              <button className="confirm-btn" onClick={confirmReport}>
                Отчети
              </button>
              <button
                className="cancel-btn"
                onClick={() => setShowReportModal(false)}
              >
                Отказ
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
