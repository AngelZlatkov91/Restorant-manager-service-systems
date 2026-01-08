import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useGetPersonalById } from "../../hooks/usePersonal";


export default function PersonalEdit() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [personalData, setPersonalData] = useState({
    name: "",
    password: "",
    role: "",
    active: false,
  });

  const [personal] = useGetPersonalById(id);
  // const updatePersonal = useUpdatePersonal();

  useEffect(() => {
    if (personal) {
      setPersonalData({
        name: personal.name,
        password: personal.password,
        role: personal.role,
        active: personal.active,
      });
    }
  }, [personal]);

  const changeHandler = (e) => {
    const { name, value, type, checked } = e.target;
    setPersonalData((state) => ({
      ...state,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const submitHandler = async (e) => {
    e.preventDefault();
    // await updatePersonal(id, personalData);
    navigate("/personal");
  };

  return (
    <section className="edit-section">
      <h2>Edit Personal</h2>

      <form className="edit-form" onSubmit={submitHandler}>
        <label>
          Name
          <input
            type="text"
            name="name"
            value={personalData.name}
            onChange={changeHandler}
            required
          />
        </label>

        <label>
          Password
          <input
            type="text"
            name="password"
            value={personalData.password}
            onChange={changeHandler}
            max={4}
            
          />
        </label>

        <label>
          Role
          <select
            name="role"
            value={personalData.role}
            onChange={changeHandler}
          >
            <option value="">Select role</option>
            <option value="ADMIN">ADMIN</option>
            <option value="MANAGER">MANAGER</option>
            <option value="PERSONAL">PERSONAL</option>
          </select>
        </label>

        <label className="checkbox">
          <input
            type="checkbox"
            name="active"
            checked={personalData.active}
            onChange={changeHandler}
          />
          Active
        </label>

        <div className="form-actions">
          <button type="submit" className="btn btn-primary">
            Save
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
