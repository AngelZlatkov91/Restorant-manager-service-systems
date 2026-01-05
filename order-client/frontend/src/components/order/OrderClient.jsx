import { useState, useEffect, use } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MenuModal from "./MenuModal";
import PaymentModal from "./PaymentModal";
import SplitOrderModal from "./SplitOrderModal";
import ConfirmDeleteModal from "./ConfirmDeleteModal";
import { useCreateOrder, useDeleteProduct, useGetActiveOrder, useUpdateOrder } from "../../hooks/useOrder";

export default function OrderClient() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [personal, setPersonal] = useState("");
  const [activeOrder, fetchOrder] = useGetActiveOrder(id);
  const [newProduct, setNewProduct] = useState([]);
  const [showMenu, setShowMenu] = useState(false);
  const [showPayment, setShowPayment] = useState(false);
  const [showSplit, setShowSplit] = useState(false);
  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
  const [productToDelete, setProductToDelete] = useState(null);

  const currentProducts = activeOrder.products || [];
  const hasProduct = activeOrder.id ? true : false;
  const totalPrice = activeOrder.totalPrice || 0;
  const tableName = activeOrder.tableName || "Маса " + id;
  const [ownerName, setOwnerName] = useState(false);

  useEffect(() => {
    (async () => {
      const token = await window.electronAPI.getToken(); 
      setPersonal(token || "Гост");
      fetchOrder();
    })();
  }, []);

  useEffect(() => {
    if (activeOrder.personalName === personal) {
      setOwnerName(true);
      return;
    } else { 
      setOwnerName(false);
    }
  },[]);

   console.log(activeOrder);
  const addProductNewProduct = (product) => {
    const exists = newProduct.find(p => p.id === product.id);
    if (exists) {
      setNewProduct(newProduct.map(p =>
        p.id === product.id ? { ...p, quantity: p.quantity + 1 } : p
      ));
    } else {
      setNewProduct([...newProduct, { ...product, quantity: 1 }]);
    }
  };

  const removeAddedProduct = async (password, product) => {
  const findIndex = activeOrder.products.findIndex(
    p => p.id === product.id && p.addedAt === product.addedAt
  );
  const data = {
    orderId: activeOrder.id,
    password: password,
    indexProduct: findIndex
  };
  await useDeleteProduct(data);
  await fetchOrder();
  setShowConfirmDelete(false);
};


  const removeNewProductNotCheck = (product) => {
    setNewProduct(newProduct.filter(p => p.id !== product.id));
  };

  const createOrUpdate = () => {
    if (newProduct.length > 0 && !hasProduct) {
      useCreateOrder({
        id,
        personalName: personal,
        products: newProduct
      });
    } else if (newProduct.length > 0 && hasProduct) {
      useUpdateOrder({ id: activeOrder.id, products: newProduct });
    }
        navigate("/tables", { state: { refresh: Date.now() } });
  };

  const handleLogout = () => window.electronAPI.logout();

  return (
    <div className="order-wrapper">
      <header className="order-header">
        <div className="order-header-left">
          <h2 style={{color: "black"}}>{tableName}</h2>
          <span className="total-price">{totalPrice} EUR</span>
        </div>
        <div className="order-header-right">
          <span style={{color: "black"}} >{personal}</span>
          {!hasProduct && (<button className="logout-btn" onClick={handleLogout}>Изход</button>)}
        </div>
      </header>

      <div className="order-main">
        <div className="order-products">
          {(currentProducts.length + newProduct.length === 0) && (
            <p>Няма избрани продукти.</p>
          )}

          {currentProducts.map(p => (
            <div key={p.name + p.addedAt} className="product-card busy">
              <div className="product-top">
                <span>{p.name} × {p.quantity} - {p.price}</span>
                <button className="delete-btn" onClick={() => { setProductToDelete(p); setShowConfirmDelete(true); }}>Изтрий</button>
              </div>
              {p.description && <span className="product-note">Бележка: {p.description}</span>}
            </div>
          ))}

          {newProduct.map(p => (
            <div key={p.id + p.name} className="product-card free">
              <div className="product-top">
                <span>{p.name} × {p.quantity}</span>
                <button className="delete-btn" onClick={() => removeNewProductNotCheck(p)}>Изтрий</button>
              </div>
              {p.description && <span className="product-note">Бележка: {p.description}</span>}
            </div>
          ))}
        </div>

        <div className="order-actions">
          <button onClick={() => setShowMenu(true)}>Добави продукт</button>
          <button onClick={() => setShowSplit(true)}>Раздели поръчка</button>
         {hasProduct && (<button onClick={() => setShowPayment(true)}>Плащане</button>)} 
          <button className="save-btn" onClick={createOrUpdate}>Запази / Актуализирай</button>
        </div>
      </div>

      {showMenu && <MenuModal onClose={() => setShowMenu(false)} onAdd={addProductNewProduct} />}
      {showPayment && <PaymentModal onClose={() => setShowPayment(false)} products={activeOrder} />}
      {showSplit && <SplitOrderModal onClose={() => setShowSplit(false)} order={activeOrder} onUpdate={addProductNewProduct} />}
      {showConfirmDelete &&
        <ConfirmDeleteModal
          product={productToDelete}
          onClose={() => setShowConfirmDelete(false)}
          onConfirm={removeAddedProduct}
        />}
    </div>
  );
}
