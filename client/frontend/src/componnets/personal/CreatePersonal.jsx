import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCreatePersonal } from "../../hooks/usePersonal";
import { useForm } from "../../hooks/useForm";


const initialValues = {
  name: "",
  password: "",
  role: "",
};

export default function CreatePersonal() {
  const navigate = useNavigate();
  const createPersonal = useCreatePersonal();

  const roles = [
    { value: "PERSONAL", label: "PERSONAL" },
    { value: "ADMIN", label: "ADMIN" },
  ];

  const createHandler = async (values) => {
      const result = await createPersonal(values);
      if (result === "Created") {
        navigate("/personal");
      }
  };

  const { values, changeHandler, submitHandler } = useForm(
    initialValues,
    createHandler
  );

  return (
    <section className="create-section">
      <h2>Create Personal</h2>

      <form className="create-form" onSubmit={submitHandler}>
        <label>
          Name
          <input
            type="text"
            name="name"
            value={values.name}
            onChange={changeHandler}
            required
          />
        </label>

        <label>
          Password
          <input
            type="password"
            name="password"
            value={values.password}
            onChange={changeHandler}
            required
          />
        </label>

        <label>
          Role
          <select
            name="role"
            value={values.role}
            onChange={changeHandler}
            required
          >
            <option value="">-- Select role --</option>
            {roles.map((role) => (
              <option key={role.value} value={role.value}>
                {role.label}
              </option>
            ))}
          </select>
        </label>

        

        <div className="form-actions">
          <button type="submit" className="btn btn-primary">
            Create
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={() => navigate("/personal")}
          >
            Cancel
          </button>
        </div>
      </form>
    </section>
  );
}
