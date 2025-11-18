import { useState } from "react";
import { useCompleteOrder } from "../../hooks/useOrder";
import { useNavigate } from "react-router-dom";

export default function PaymentModal({ onClose, products }) {
  const navigate = useNavigate();
  const [method, setMethod] = useState("CASH");

  const total = products.totalPrice;

  const handlePayment = () => {
    const data = {
      id: products.id,
      paymentMethod: method,
      personal: products.personalName

    }
    const result = useCompleteOrder(data);
    navigate("/tables");
    alert(`Платено ${total} лв. с ${method}`);
    onClose();
  };

  return (
    <div style={modalStyle}>
      <h2>Плащане</h2>
      <p>Обща сума: {total} лв.</p>
      <div>
        <label>
          <input type="radio" name="method" value="CASH" checked={method === "CASH"} onChange={() => setMethod("CASH")} />
          В брой
        </label>
        <label style={{ marginLeft: "10px" }}>
          <input type="radio" name="method" value="CARD" checked={method === "CARD"} onChange={() => setMethod("CARD")} />
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
