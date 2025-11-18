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
}
let initialValuesToUpdate = {
  id: '',
  products: [],
}
export default function OrderClient() {
 
  const [newProduct, setNewProduct] = useState([]);
  const [personal, setPersonal] = useState('');
  const navigate = useNavigate();
  const { id } = useParams();
  const [activeOrder, setActiveOrder] = useGetActiveOrder(id);
  let currentProducts = activeOrder.products;

  let hasProduct = false;
  let price = 0.0;
  if (activeOrder.id) {
    hasProduct = true;
    price = activeOrder.totalPrice;
  };
 
  const [showMenu, setShowMenu] = useState(false);
  const [showPayment, setShowPayment] = useState(false);
  const [showSplit, setShowSplit] = useState(false);
  const [showConfirmDelete, setShowConfirmDelete] = useState(false);
  const [productToDelete, setProductToDelete] = useState(null);
  const [dataToDelete, setDataToDelete] = useState();
  useEffect(() => {
      (async () => {
        const token = await getAccessToken();
        setPersonal(token);
      })();
    }, []);

  // Добавяне продукт
  const addProduct = (product) => {
    const exists = newProduct.find(p => p.id === product.id);
    if (exists) {
      setNewProduct(newProduct.map(p => 
        p.id === product.id ? {...p, quantity: p.quantity + 1} : p
      ));
    } else {
      setNewProduct([...newProduct, product]);
    }
  };



  // Изтриване продукт
  const removeProduct = (password, product) => {
    const findIndex = activeOrder.products.findIndex(p => p.id === product.id && p.addedAt === product.addedAt);
    const data = {
      orderId: activeOrder.id,
      password: password,
      indexProduct: findIndex
    }
    const result = useDeleteProduct(data);
    console.log(result);

   setShowConfirmDelete(false);
  };

  const removeNewProductNotCheck = (product) => {
    console.log(`order ${product}`)
    setNewProduct(newProduct.filter(p => p.id !== product.id));
  }

  // Актуализира продукт (например при разделяне)
  const updateProduct = (updatedProduct) => {
    setActiveOrder(
      activeOrder.map(p => p.id === updatedProduct.id ? updatedProduct : p)
    );
  };

  const getToAllTables = () => {
    if (newProduct.length > 0 && !hasProduct) {
    initialValuesToCreate.personalName = personal;
    initialValuesToCreate.id = id;
    initialValuesToCreate.products = newProduct;
     const createNewOrder = useCreateOrder(initialValuesToCreate);
    
    }

    if (newProduct.length > 0 && hasProduct) {
      initialValuesToUpdate.id = activeOrder.id;
      initialValuesToUpdate.products = newProduct;
      const updateOrder = useUpdateOrder(initialValuesToUpdate);
    }
    navigate('/tables');
  }

  return (
    <>
    <h1>{personal}</h1>
    <div style={{ display: "flex", gap: "20px", padding: "20px" }}>
      {/* Текуща поръчка */}
      <div style={{ flex: 1, border: "1px solid #ccc", padding: "10px", borderRadius: "10px" }}>
        <h2>Текуща поръчка: <strong>{price} : EURO</strong></h2>
        {!hasProduct ? (
          <p>Няма избрани продукти.</p>
        ) : (
          currentProducts.map(p => (
            <div key={p.name} style={{ display: "flex", justifyContent: "space-between", marginBottom: "8px", padding: "5px", borderRadius: "5px", background: "#f0f0f0" }}>
              <span>{p.name} - <span style={{color: "red"}}>{p.quantity}</span></span>
              <div style={{ display: "flex", gap: "5px" }}>
                <button 
                  onClick={() => { setProductToDelete(p); setShowConfirmDelete(true);
                    
                   }}
                  style={{ background: "#f44336", color: "white", border: "none", borderRadius: "5px", padding: "4px 8px" }}
                >Изтрий</button>
              </div>
            </div>
          ))
        )}

        {!newProduct ? (
          <p>Няма избрани продукти.</p>
        ) : (
          newProduct.map(p => (
            <div key={p.name} style={{ display: "flex", justifyContent: "space-between", marginBottom: "8px", padding: "5px", borderRadius: "5px", background: "#f0f0f0" }}>
              <span style={{color: "green"}}>{p.name} - <span style={{color: "red"}}>{p.quantity}</span></span>
              <div style={{ display: "flex", gap: "5px" }}>
                <button 
                  onClick={() =>removeNewProductNotCheck(p)}
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
      {showSplit && <SplitOrderModal onClose={() => setShowSplit(false)} order={activeOrder} onUpdate={updateProduct} />}
      {showConfirmDelete && 
        <ConfirmDeleteModal 
          product={productToDelete} 
          index={dataToDelete}
          onClose={() => setShowConfirmDelete(false)} 
          onConfirm={removeProduct} 
        />
      }
    </div>
    </>
    
  );
}
