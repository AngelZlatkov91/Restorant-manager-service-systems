import { useState } from "react";

export default function SplitOrderModal({ onClose, products, onUpdate }) {
  const [splits, setSplits] = useState(products.map(p => ({ ...p })));

  const changeQuantity = (id, qty) => {
    setSplits(splits.map(p => p.id === id ? { ...p, quantity: qty } : p));
  };

  const handleSave = () => {
    splits.forEach(p => onUpdate(p));
    onClose();
  };

  return (
    <div style={modalStyle}>
      <h2>Раздели поръчка</h2>
      {splits.map(p => (
        <div key={p.id} style={{ display: "flex", justifyContent: "space-between", marginBottom: "5px" }}>
          <span>{p.name}</span>
          <input type="number" min="0" value={p.quantity} onChange={e => changeQuantity(p.id, Number(e.target.value))} style={{ width: "60px" }} />
        </div>
      ))}
      <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
        <button onClick={handleSave}>Запази</button>
        <button onClick={onClose}>Отказ</button>
      </div>
    </div>
  );
}

const modalStyle = {
  position: "fixed",
  top: "20%",
  left: "30%",
  width: "40%",
  background: "white",
  border: "1px solid #ccc",
  borderRadius: "10px",
  padding: "20px",
  zIndex: 1000,
};
