import { useState } from "react";
import { useCreateTable } from "../../hooks/useTable";
import { useForm } from "../../hooks/useForm";
import { Link, useNavigate } from "react-router-dom";

const initialValues = {
  tableName: ""
};

export default function CreateTable() {
  const createTable = useCreateTable();
  const navigate = useNavigate();
  const [hasError, setHasError] = useState("");

  const createHandler = async (values) => {
    const result = await createTable(values);

    if (result?.resMessage) {
      setHasError(result.resMessage);
    } else {
      navigate("/table");
    }
  };

  const { values, changeHandler, submitHandler } = useForm(
    initialValues,
    createHandler
  );

  return (
    <section className="create-wrapper">
      <Link to="/table" className="back-link">← Назад към маси</Link>

      <form className="create-card" onSubmit={submitHandler}>
        <h1 className="create-title">➕ Създай маса</h1>

        <div className="input-group">
          <label htmlFor="tableName">Име на маса</label>
          <input
            type="text"
            id="tableName"
            name="tableName"
            value={values.tableName}
            onChange={changeHandler}
            placeholder="Напр. Маса 1"
            autoFocus
          />
        </div>

        {hasError && <div className="error-box">{hasError}</div>}

        <button className="primary-btn" type="submit">
          Създай
        </button>
      </form>
    </section>
  );
}
