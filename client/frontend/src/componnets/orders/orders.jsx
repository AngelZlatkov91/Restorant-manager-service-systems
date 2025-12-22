import { useEffect, useState } from "react";
import { useOrder } from "../../hooks/useOrder";


export default function Orders() {
  const [orders, loadOrders] = useOrder();
  const [filteredOrders, setFilteredOrders] = useState([]);
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [totalRevenue, setTotalRevenue] = useState(0);

  useEffect(() => {
    loadOrders();
  }, []);

  useEffect(() => {
    if (orders) {
      setFilteredOrders(orders);
      const total = orders.reduce((acc, o) => acc + o.totalPrice, 0);
      setTotalRevenue(total);
    }
  }, [orders]);

  const handleFilter = () => {
    const from = new Date(fromDate);
    const to = new Date(toDate);
    const filtered = orders.filter((order) => {
      const created = new Date(order.createdAd);
      return created >= from && created <= to;
    });
    setFilteredOrders(filtered);
    const total = filtered.reduce((acc, o) => acc + o.totalPrice, 0);
    setTotalRevenue(total);
  };

  return (
    <section className="orders-page">
      <header className="orders-header">
        <h2>Всички поръчки</h2>
        <div className="revenue">Общ приход: {totalRevenue.toFixed(2)} €</div>
        <div className="date-filters">
          <input
            type="date"
            value={fromDate}
            onChange={(e) => setFromDate(e.target.value)}
          />
          <input
            type="date"
            value={toDate}
            onChange={(e) => setToDate(e.target.value)}
          />
          <button onClick={handleFilter}>Филтрирай</button>
        </div>
      </header>

      {filteredOrders && filteredOrders.length > 0 ? (
        <ul className="orders-list">
          {filteredOrders.map((order) => (
            <li key={order.id} className="order-card">
              <div className="order-header">
                <div className="order-meta">
                  <span className="order-table">{order.tableName}</span>
                  <span className="order-personal">{order.personalName}</span>
                  <span className={`order-status ${order.status.toLowerCase()}`}>
                    {order.status}
                  </span>
                </div>
                <div className="order-date">
                  {new Date(order.createdAd).toLocaleString()}
                </div>
              </div>

              <ul className="products-list">
                {order.products.map((p, index) => (
                  <li
                    key={`${p.id}-${p.addedAt}-${index}`}
                    className={`product-item ${p.typeProduct.toLowerCase()}`}
                  >
                    <span className="product-name">{p.name}</span>
                    <span className="product-qty">{p.quantity} ×</span>
                    <span className="product-price">{p.price.toFixed(2)} €</span>
                  </li>
                ))}
              </ul>

              <div className="order-total">
                <strong>Общо: {order.totalPrice.toFixed(2)} €</strong>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className="empty-state">Няма налични поръчки.</p>
      )}
    </section>
  );
}
