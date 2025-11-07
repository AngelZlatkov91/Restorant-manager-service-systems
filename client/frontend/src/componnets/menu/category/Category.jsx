import { useEffect } from "react";
import { useDeleteCategory, useGetAllCategory } from "../../../hooks/useCategory";
import { useNavigate } from "react-router-dom";


export default function Category() {
  const navigate = useNavigate();
 
   const [categories, refreshCategories] = useGetAllCategory();

 const createCategoryHandler = () => {
  navigate('/createCategory');
 }
  const handleDelete = async (id) => { 
   
    useDeleteCategory(id); 
  };
    refreshCategories();


   return (
    <section id="category" style={{ padding: "20px" }}>
      <h2>Категории</h2>
      <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={createCategoryHandler} style={{
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "5px",
                  background: "#4caf50",
                  color: "white",
                  cursor: "pointer",
                }}>Create Category</button>
              </div>
      <ul style={{ listStyle: "none", padding: 0 }}>
        {categories && categories.length > 0 ? (
          categories.map((cat) => (
            <li
              key={cat.id}
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                background: "#f9f9f9",
                marginBottom: "10px",
                padding: "10px 15px",
                borderRadius: "8px",
                boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
              }}
            >
              <p style={{ margin: 0, fontWeight: "500" }}>{cat.category}</p>

              <div style={{ display: "flex", gap: "10px" }}>
                <button
                  onClick={() => handleEdit(cat.id)}
                  style={{
                    padding: "6px 12px",
                    border: "none",
                    borderRadius: "5px",
                    background: "#4caf50",
                    color: "white",
                    cursor: "pointer",
                  }}
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(cat.id)}
                  style={{
                    padding: "6px 12px",
                    border: "none",
                    borderRadius: "5px",
                    background: "#f44336",
                    color: "white",
                    cursor: "pointer",
                  }}
                >
                  Delete
                </button>
              </div>
            </li>
          ))
        ) : (
          <p>Няма налични категории.</p>
        )}
      </ul>
    </section>
  )
}