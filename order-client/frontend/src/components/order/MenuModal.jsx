import { useState, useEffect } from "react";
import { useGetAllMenuItems } from "../../hooks/useItem";

export default function MenuModal({ onClose, onAdd }) {
  const [filter, setFilter] = useState("");
  const [items, setItems] = useGetAllMenuItems();
  
  useEffect(() => {
    setItems(items.filter(item => item.name.toLowerCase().includes(filter.toLowerCase())));
  }, [filter]);

  return (
    <div style={{ position: "fixed", top: "10%", left: "30%", width: "40%", background: "white", border: "1px solid #ccc", borderRadius: "10px", padding: "20px", zIndex: 1000 }}>
      <h2>Меню</h2>
      <input type="text" placeholder="Търсене..." value={filter} onChange={e => setFilter(e.target.value)} style={{ width: "100%", padding: "5px", marginBottom: "10px" }} />
      <ul style={{ listStyle: "none", padding: 0 }}>
        {items.map(item => (
          <li key={item.id} style={{ display: "flex", justifyContent: "space-between", marginBottom: "5px" }}>
            <span>{item.name} - ${item.price}</span>
            <button onClick={() => onAdd({ ...item, quantity: 1 })}>Добави</button>
          </li>
        ))}
      </ul>
      <button onClick={onClose} style={{ marginTop: "10px" }}>Затвори</button>
    </div>
  );
}
