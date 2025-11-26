import { useState } from "react";
import { useDeleteMenuItem, useGetAllMenuItems } from "../../../hooks/useItem";
import ConfirmPopup from "../../confirmModal/ConfirmPop";
import { useNavigate } from "react-router-dom";

export default function Item() {
  const navigate = useNavigate();
  const [items, fetchItems] = useGetAllMenuItems(); 
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);
  const [popupPos, setPopupPos] = useState({ top: 0, left: 0 });

  const handleDeleteClick = (e,item) => {
    const rect = e.target.getBoundingClientRect(); // позиция на бутона
     setPopupPos({ top: rect.top + window.scrollY + 30, left: rect.left + rect.width / 2 });
  
    setItemToDelete(item);
    setIsConfirmOpen(true);
  };

  const handleConfirm = async () => {
    if (!itemToDelete) return;
    await useDeleteMenuItem(itemToDelete.id);
    setIsConfirmOpen(false);
    setItemToDelete(null);
  };

  const handleCancel = () => {
    setIsConfirmOpen(false);
    setItemToDelete(null);
  };

  fetchItems();

const handleEdit = (id) => {
    navigate(`/editItem/${id}`);
  };
   const createItemHandler = () => {
      navigate("/createItem");
    }

 

  return (
    <section id="items" style={{ padding: "20px" }}>
      <h2>Меню артикули</h2>
             <div style={{ display: "flex", gap: "10px" }}>
                <button onClick={createItemHandler} style={{
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "5px",
                  background: "#4caf50",
                  color: "white",
                  cursor: "pointer",
                }}>Create Item</button>
              </div>
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
                <span style={{ color: item.active ? "black" : "red" }}>
                   <strong>{item.name}</strong>
                </span>
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
                  onClick={(e) => handleDeleteClick(e,item)}
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

      <ConfirmPopup
          isOpen={isConfirmOpen}
           message={`Сигурен ли си, че искаш да изтриеш "${itemToDelete?.name}"?`}
          onConfirm={handleConfirm}
          onCancel={handleCancel}
         position={{ top: "50%", left: "50%" }}
        />
    </section>
  );
}
