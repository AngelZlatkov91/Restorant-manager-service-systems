import { useState, useEffect } from "react";
import MenuModal from "./MenuModal";
import PaymentModal from "./PaymentModal";
import SplitOrderModal from "./SplitOrderModal";
import ConfirmDeleteModal from "./ConfirmDeleteModal";
import { useNavigate, useParams } from "react-router-dom";
import { useCreateOrder, useDeleteProduct, useGetActiveOrder, useUpdateOrder } from "../../hooks/useOrder";
import { getAccessToken } from "../../utils/authUtils";

let initialValuesToCreate = {
  id: '',
  personalName: '',
  products: [],
};

let initialValuesToUpdate = {
  id: '',
  products: [],
};

export default function OrderClient() {
  const [newProduct, setNewProduct] = useState([]);
  const [personal, setPersonal] = useState('');
  const navigate = useNavigate();
  const { id } = useParams();
  const [activeOrder, fetchOrder] = useGetActiveOrder(id);
  let currentProducts = activeOrder.products || [];
  let hasProduct = activeOrder.id ? true : false;
  let price = activeOrder.totalPrice || 0;

  // Модални прозорци
  const [showMenu, setShowMenu] = useState(false);
  const [showPayment, setShowPayment] = useState(false);
  const [showSplit, setShowSplit] = useState(false);
  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
  const [productToDelete, setProductToDelete] = useState(null);

  // Получаване на персонала (JWT токен)
  useEffect(() => {
    (async () => {
      const token = await getAccessToken();
      setPersonal(token);
    })();
  }, []);

  // Добавяне на нов продукт към текущата поръчка
  const addProductNewProduct = (product) => {
    const exists = newProduct.find(p => p.id === product.id);
    if (exists) {
      setNewProduct(newProduct.map(p => 
        p.id === product.id ? {...p, quantity: p.quantity + 1} : p
      ));
    } else {
      setNewProduct([...newProduct, {...product, quantity: 1}]);
    }
  };

  // Премахване на продукт от вече създадена поръчка
  const removeAddedProduct = (password, product) => {
    const findIndex = activeOrder.products.findIndex(p => p.id === product.id && p.addedAt === product.addedAt);
    const data = {
      orderId: activeOrder.id,
      password: password,
      indexProduct: findIndex
    };
    useDeleteProduct(data);
    fetchOrder();
    setShowConfirmDelete(false);
  };

  // Премахване на продукт от новите продукти
  const removeNewProductNotCheck = (product) => {
    setNewProduct(newProduct.filter(p => p.id !== product.id));
  };

  // Актуализиране на продукт (например бележка)
  const updateProduct = (updatedProduct) => {
    setActiveOrder(
      activeOrder.products.map(p => p.id === updatedProduct.id ? updatedProduct : p)
    );
  };

  // Създаване или ъпдейт на поръчка
  const createOrUpdate = () => {
    if (newProduct.length > 0 && !hasProduct) {
      initialValuesToCreate.personalName = personal;
      initialValuesToCreate.id = id;
      initialValuesToCreate.products = newProduct;
      useCreateOrder(initialValuesToCreate);
    }

    if (newProduct.length > 0 && hasProduct) {
      initialValuesToUpdate.id = activeOrder.id;
      initialValuesToUpdate.products = newProduct;
      useUpdateOrder(initialValuesToUpdate);
    }

    navigate('/tables');
  };

  return (
    <>
      <h1>{personal}</h1>
      <div style={{ display: "flex", gap: "20px", padding: "20px" }}>
        {/* Текуща поръчка */}
        <div style={{ flex: 1, border: "1px solid #ccc", padding: "10px", borderRadius: "10px" }}>
          <h2>Текуща поръчка: <strong>{price} : EURO</strong></h2>

          {/* Вече съществуващи продукти */}
          {currentProducts.length === 0 ? (
            <p>Няма избрани продукти.</p>
          ) : (
            currentProducts.map(p => (
              <div
                key={p.name + p.addedAt}
                style={{
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "space-between",
                  marginBottom: "8px",
                  padding: "10px",
                  borderRadius: "5px",
                  background: "#f0f0f0",
                  border: "1px solid #ddd"
                }}
              >
                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                  <span>{p.name} - <span style={{color: "red"}}>{p.quantity}</span> - <span>{p.price}</span></span>
                  <div style={{ display: "flex", gap: "5px" }}>
                    <button
                      onClick={() => { setProductToDelete(p); setShowConfirmDelete(true); }}
                      style={{ background: "#f44336", color: "white", border: "none", borderRadius: "5px", padding: "4px 8px", cursor: "pointer" }}
                    >
                      Изтрий
                    </button>
                  </div>
                </div>
                {p.description && (
                  <span style={{ marginTop: "5px", fontSize: "0.85em", fontStyle: "italic", color: "#555" }}>
                    Бележка: {p.description}
                  </span>
                )}
              </div>
            ))
          )}

          {/* Нови добавени продукти */}
          {newProduct.length > 0 && newProduct.map(p => (
            <div
              key={p.name + p.id}
              style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                marginBottom: "8px",
                padding: "10px",
                borderRadius: "5px",
                background: "#e8f5e9",
                border: "1px solid #c8e6c9"
              }}
            >
              <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                <span style={{color: "green"}}>{p.name} - <span style={{color: "red"}}>{p.quantity}</span></span>
                <div style={{ display: "flex", gap: "5px" }}>
                  <button
                    onClick={() => removeNewProductNotCheck(p)}
                    style={{ background: "#f44336", color: "white", border: "none", borderRadius: "5px", padding: "4px 8px", cursor: "pointer" }}
                  >
                    Изтрий
                  </button>
                </div>
              </div>
              {p.description && (
                <span style={{ marginTop: "5px", fontSize: "0.85em", fontStyle: "italic", color: "#555" }}>
                  Бележка: {p.description}
                </span>
              )}
            </div>
          ))}

          {/* Действия с поръчката */}
          <div style={{ marginTop: "20px", display: "flex", gap: "10px" }}>
            <button onClick={() => setShowMenu(true)} style={{ padding: "6px 12px" }}>Добави продукт</button>
            <button onClick={() => setShowSplit(true)} style={{ padding: "6px 12px" }}>Раздели поръчка</button>
            <button onClick={() => setShowPayment(true)} style={{ padding: "6px 12px" }}>Плащане</button>
          </div>
        </div>

        <button onClick={createOrUpdate} style={{ padding: "6px 12px" }}>Запази / Актуализирай</button>

        {/* Модали */}
        {showMenu && <MenuModal onClose={() => setShowMenu(false)} onAdd={addProductNewProduct} />}
        {showPayment && <PaymentModal onClose={() => setShowPayment(false)} products={activeOrder} />}
        {showSplit && <SplitOrderModal onClose={() => setShowSplit(false)} order={activeOrder} onUpdate={updateProduct} />}
        {showConfirmDelete && 
          <ConfirmDeleteModal 
            product={productToDelete} 
            onClose={() => setShowConfirmDelete(false)} 
            onConfirm={removeAddedProduct} 
          />
        }
      </div>
    </>
  );
}
