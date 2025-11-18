import { useState } from "react";

export default function ConfirmDeleteModal({product, onClose, onConfirm }) {
  const [password, setPassword] = useState("");

  const handleConfirm = () => {
    if (password.startsWith("0000")) { // Примерна админ парола

      onConfirm(password,product);
    } else {
      alert("Невалидна парола!");
    }
  };

  return (
    <div style={modalStyle}>
      <h2>Изтрий продукт</h2>
      <p>Сигурни ли сте, че искате да изтриете {product?.name}?</p>
      <input type="password" placeholder="Парола на админ" value={password} onChange={e => setPassword(e.target.value)} />
      <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
        <button onClick={handleConfirm}>Потвърди</button>
        <button onClick={onClose}>Отказ</button>
      </div>
    </div>
  );
}

const modalStyle = {
  position: "fixed",
  top: "25%",
  left: "35%",
  width: "30%",
  background: "white",
  border: "1px solid #ccc",
  borderRadius: "10px",
  padding: "20px",
  zIndex: 1000,
};
