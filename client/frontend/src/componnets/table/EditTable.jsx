import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useGetTableById} from "../../hooks/useTable";


export default function EditTable() {
  const { id } = useParams();
  const navigate = useNavigate();

  const table = useGetTableById(id);

  const [tableName, setTableName] = useState("");

  useEffect(() => {
    if (table) {
      setTableName(table.tableName);
    }
  }, [table]);

  const handleSave = async () => {
    if (!tableName.trim()) return;
    navigate("/table"); 
  };

  const handleCancel = () => {
    navigate("/table");
  };

  return (
    <section className="edit-table-section">
      <h2>Edit Table</h2>

      <div className="edit-table-form">
        <label>
          Table Name
          <input
            type="text"
            value={tableName}
            onChange={(e) => setTableName(e.target.value)}
          />
        </label>

        <div className="form-actions">
          <button className="btn btn-primary" onClick={handleSave}>
            Save
          </button>
          <button className="btn btn-secondary" onClick={handleCancel}>
            Cancel
          </button>
        </div>
      </div>
    </section>
  );
}
