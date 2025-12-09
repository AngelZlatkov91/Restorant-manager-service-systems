import { useState, useEffect } from "react";
import { useGetAllMenuItems } from "../../hooks/useItem";

export default function MenuModal({ onClose, onAdd }) {
  const [filter, setFilter] = useState("");
  const [items] = useGetAllMenuItems();
  const [filteredItems, setFilteredItems] = useState([]);
  const [noteMap, setNoteMap] = useState({}); // за бележките

  useEffect(() => {
    setFilteredItems(
      items.filter(item =>
        item.name.toLowerCase().includes(filter.toLowerCase())
      )
    );
  }, [filter, items]);

  const handleNoteChange = (id, value) => {
    setNoteMap(prev => ({ ...prev, [id]: value }));
  };

  const handleAddProduct = (item) => {
    const description = noteMap[item.id] || "";
    onAdd({ ...item, quantity: 1, description });
    setNoteMap(prev => ({ ...prev, [item.id]: "" })); // изчистване след добавяне
  };

  return (
    <div style={{
      position: "fixed",
      top: "10%",
      left: "30%",
      width: "40%",
      background: "white",
      border: "1px solid #ccc",
      borderRadius: "10px",
      padding: "20px",
      zIndex: 1000,
      boxShadow: "0 5px 15px rgba(0,0,0,0.3)"
    }}>
      <h2 style={{ marginBottom: "15px" }}>Меню</h2>

      <input
        type="text"
        placeholder="Търсене..."
        value={filter}
        onChange={e => setFilter(e.target.value)}
        style={{
          width: "100%",
          padding: "8px",
          marginBottom: "15px",
          borderRadius: "5px",
          border: "1px solid #ccc"
        }}
      />

      <ul style={{ listStyle: "none", padding: 0, maxHeight: "400px", overflowY: "auto" }}>
        {filteredItems.map(item => (
          <li key={item.id} style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            marginBottom: "8px",
            padding: "8px",
            borderRadius: "5px",
            background: "#f9f9f9",
            border: "1px solid #ddd"
          }}>
            <div>
              <strong>{item.name}</strong> - ${item.price}
              <input
                type="text"
                placeholder="Бележка (по желание)"
                value={noteMap[item.id] || ""}
                onChange={e => handleNoteChange(item.id, e.target.value)}
                style={{
                  display: "block",
                  marginTop: "5px",
                  padding: "4px 6px",
                  fontSize: "0.85em",
                  borderRadius: "4px",
                  border: "1px solid #ccc",
                  width: "200px"
                }}
              />
            </div>

            <button
              onClick={() => handleAddProduct(item)}
              style={{
                background: "#4caf50",
                color: "white",
                border: "none",
                borderRadius: "5px",
                padding: "6px 12px",
                cursor: "pointer"
              }}
            >
              Добави
            </button>
          </li>
        ))}
      </ul>

      <button
        onClick={onClose}
        style={{
          marginTop: "15px",
          padding: "8px 12px",
          borderRadius: "5px",
          border: "none",
          background: "#f44336",
          color: "white",
          cursor: "pointer"
        }}
      >
        Затвори
      </button>
    </div>
  );
}
