import { useState } from "react";
import { useDeleteMenuItem, useGetAllMenuItems } from "../../../hooks/useItem";


export default function Item() {
  const [items, fetchItems] = useGetAllMenuItems(); 

  const handleDelete = async (id) => {
     const result = await useDeleteMenuItem(id);
     console.log(result.status)
  };

  fetchItems();

  const handleEdit = (id) => {
    console.log("Edit item id:", id);
    // тук можеш да навигираш към форма за редакция
  };

  return (
    <section id="items" style={{ padding: "20px" }}>
      <h2>Меню артикули</h2>
      {items && items.length > 0 ? (
        <ul style={{ listStyle: "none", padding: 0 }}>
          {items.map((item) => (
            <li
              key={item.id} // ✅ уникален key
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
              <div>
                <strong>{item.name}</strong>
                <p style={{ margin: "5px 0" }}>
                  💰 Цена: {item.price} лв. | 🏷️ Категория: {item.category || "Няма"} | 🍽️ Тип: {item.typeProduct}
                </p>
              </div>

              <div style={{ display: "flex", gap: "10px" }}>
                <button
                  onClick={() => handleEdit(item.id)}
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
                  onClick={() => handleDelete(item.id)}
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
          ))}
        </ul>
      ) : (
        <p>Няма налични артикули.</p>
      )}
    </section>
  );
}
