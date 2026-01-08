import { useNavigate } from "react-router-dom";
import { useChangePersonalStatus, useGetAllPersonal } from "../../hooks/usePersonal";


export default function Personal() {
  const navigate = useNavigate();
  const [personals, fetchPersonal] = useGetAllPersonal();
  const setStatus = useChangePersonalStatus();

  const changeStatus = async (id) => {
    await setStatus(id);
    fetchPersonal();
  };

  return (
    <section className="personal-section">
      <div className="personal-header">
        <h2>Персонал</h2>
        <button className="btn btn-primary" onClick={() => navigate("/createPersonal")}>
          + Create Personal
        </button>
      </div>

      {personals && personals.length > 0 ? (
        <ul className="personal-list">
          {personals.map((personal) => (
            <li key={personal.id} className="personal-card">
              <div className="personal-info">
                <h3>{personal.name}</h3>
                <p><strong>Password:</strong> {personal.password}</p>
                <p><strong>Role:</strong> {personal.role}</p>
                <p>
                  <strong>Status:</strong>{" "}
                  <span className={personal.active ? "status active" : "status inactive"}>
                    {personal.active ? "Active" : "Inactive"}
                  </span>
                </p>
              </div>

              <div className="personal-actions">
                <button
                
                  className="btn btn-secondary"
                  onClick={() => navigate(`/editPersonal/${personal.id}`)}
                >
                  Edit
                </button>

                <button
                  className="btn btn-warning"
                  onClick={() => changeStatus(personal.id)}
                >
                  Change Status
                </button>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className="empty-text">Няма налични персонали.</p>
      )}
    </section>
  );
}
