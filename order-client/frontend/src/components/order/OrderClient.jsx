import { useState } from "react";
import MenuModal from "./MenuModal";
import PaymentModal from "./PaymentModal";
import SplitOrderModal from "./SplitOrderModal";
import ConfirmDeleteModal from "./ConfirmDeleteModal";
import { useNavigate, useParams } from "react-router-dom";
import { useGetAllActiveOrder } from "../../hooks/useOrder";

export default function OrderClient() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [activeOrder, setActiveOrder] = useGetAllActiveOrder(id);
  let hasProduct = true;
  if (!activeOrder.id) {
    hasProduct = false;
  };
  const [showMenu, setShowMenu] = useState(false);
  const [showPayment, setShowPayment] = useState(false);
  const [showSplit, setShowSplit] = useState(false);
  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
  const [productToDelete, setProductToDelete] = useState(null);

  // Добавяне продукт
  const addProduct = (product) => {
    // Ако продуктът вече съществува, увеличи quantity
    const exists = activeOrder.find(p => p.id === product.id);
    if (exists) {
      setActiveOrder(activeOrder.map(p => 
        p.id === product.id ? {...p, quantity: p.quantity + 1} : p
      ));
    } else {
      setActiveOrder([...activeOrder, product]);
    }
  };

  // Изтриване продукт
  const removeProduct = (productId) => {
    setActiveOrder(activeOrder.filter(p => p.id !== productId));
  };

  // Актуализира продукт (например при разделяне)
  const updateProduct = (updatedProduct) => {
    setActiveOrder(
      activeOrder.map(p => p.id === updatedProduct.id ? updatedProduct : p)
    );
  };

  const getToAllTables = () => {
    navigate('/tables');
  }

  return (
    <div style={{ display: "flex", gap: "20px", padding: "20px" }}>
      {/* Текуща поръчка */}
      <div style={{ flex: 1, border: "1px solid #ccc", padding: "10px", borderRadius: "10px" }}>
        <h2>Текуща поръчка</h2>
        {!hasProduct ? (
          <p>Няма избрани продукти.</p>
        ) : (
          activeOrder.products.map(p => (
            <div key={p.name} style={{ display: "flex", justifyContent: "space-between", marginBottom: "8px", padding: "5px", borderRadius: "5px", background: "#f0f0f0" }}>
              <span>{p.name} x {p.quantity}</span>
              <div style={{ display: "flex", gap: "5px" }}>
                <button 
                  onClick={() => { setProductToDelete(p); setShowConfirmDelete(true); }}
                  style={{ background: "#f44336", color: "white", border: "none", borderRadius: "5px", padding: "4px 8px" }}
                >Изтрий</button>
              </div>
            </div>
          ))
        )}

        {/* Действия с поръчката */}
        <div style={{ marginTop: "20px", display: "flex", gap: "10px" }}>
          <button onClick={() => setShowMenu(true)} style={{ padding: "6px 12px" }}>Добави продукт</button>
          <button onClick={() => setShowSplit(true)} style={{ padding: "6px 12px" }}>Раздели поръчка</button>
          <button onClick={() => setShowPayment(true)} style={{ padding: "6px 12px" }}>Плащане</button>
        </div>
      </div>

      <button onClick={getToAllTables} style={{ padding: "6px 12px" }}>Salon</button>

      {/* Модали */}
      {showMenu && <MenuModal onClose={() => setShowMenu(false)} onAdd={addProduct} />}
      {showPayment && <PaymentModal onClose={() => setShowPayment(false)} products={activeOrder} />}
      {showSplit && <SplitOrderModal onClose={() => setShowSplit(false)} products={activeOrder} onUpdate={updateProduct} />}
      {showConfirmDelete && 
        <ConfirmDeleteModal 
          product={productToDelete} 
          onClose={() => setShowConfirmDelete(false)} 
          onConfirm={() => { removeProduct(productToDelete.id); setShowConfirmDelete(false); }} 
        />
      }
    </div>
  );
}
