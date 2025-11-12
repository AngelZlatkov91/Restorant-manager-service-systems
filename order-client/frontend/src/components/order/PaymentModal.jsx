import { useState } from "react";

export default function PaymentModal({ onClose, products }) {
  const [method, setMethod] = useState("cash");

  const total = products.reduce((sum, p) => sum + p.price * p.quantity, 0);

  const handlePayment = () => {
    alert(`Платено ${total} лв. с ${method}`);
    onClose();
  };

  return (
    <div style={modalStyle}>
      <h2>Плащане</h2>
      <p>Обща сума: {total} лв.</p>
      <div>
        <label>
          <input type="radio" name="method" value="cash" checked={method === "cash"} onChange={() => setMethod("cash")} />
          В брой
        </label>
        <label style={{ marginLeft: "10px" }}>
          <input type="radio" name="method" value="card" checked={method === "card"} onChange={() => setMethod("card")} />
          Карта
        </label>
      </div>
      <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
        <button onClick={handlePayment}>Плати</button>
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
