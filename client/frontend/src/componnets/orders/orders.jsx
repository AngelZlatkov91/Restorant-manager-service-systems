import { useEffect, useMemo, useState } from "react";
import { useOrder } from "../../hooks/useOrder";

const ITEMS_PER_PAGE = 15;

export default function Orders() {
  const [orders, loadOrders] = useOrder();

  // filters
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [personalName, setPersonalName] = useState("");
  const [minAmount, setMinAmount] = useState("");
  const [maxAmount, setMaxAmount] = useState("");

  // pagination
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    loadOrders();
  }, []);

  // 🔍 FILTERED ORDERS
  const filteredOrders = useMemo(() => {
    if (!orders) return [];

    return orders.filter((order) => {
      const created = new Date(order.createdAd);

      const matchFrom =
        !fromDate || created >= new Date(fromDate);

      const matchTo =
        !toDate || created <= new Date(toDate);

      const matchPersonal =
        !personalName ||
        order.personalName
          .toLowerCase()
          .includes(personalName.toLowerCase());

      const matchMin =
        !minAmount || order.totalPrice >= Number(minAmount);

      const matchMax =
        !maxAmount || order.totalPrice <= Number(maxAmount);

      return (
        matchFrom &&
        matchTo &&
        matchPersonal &&
        matchMin &&
        matchMax
      );
    });
  }, [orders, fromDate, toDate, personalName, minAmount, maxAmount]);

  // 📄 PAGINATION
  const totalPages = Math.ceil(filteredOrders.length / ITEMS_PER_PAGE);

  const paginatedOrders = useMemo(() => {
    const start = (currentPage - 1) * ITEMS_PER_PAGE;
    return filteredOrders.slice(start, start + ITEMS_PER_PAGE);
  }, [filteredOrders, currentPage]);

  // 💰 TOTAL REVENUE (filtered)
  const totalRevenue = useMemo(() => {
    return filteredOrders.reduce((acc, o) => acc + o.totalPrice, 0);
  }, [filteredOrders]);

  // reset page on filter change
  useEffect(() => {
    setCurrentPage(1);
  }, [fromDate, toDate, personalName, minAmount, maxAmount]);

  return (
    <section className="orders-page">
      <header className="orders-header">
        <h2>Всички поръчки</h2>
        <div className="revenue">
          Общ приход: {totalRevenue.toFixed(2)} €
        </div>

        {/* 🔍 FILTER BAR */}
        <div className="filters">
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

          <input
            type="text"
            placeholder="Персонал..."
            value={personalName}
            onChange={(e) => setPersonalName(e.target.value)}
          />

          <input
            type="number"
            placeholder="Мин. сума"
            value={minAmount}
            onChange={(e) => setMinAmount(e.target.value)}
          />

          <input
            type="number"
            placeholder="Макс. сума"
            value={maxAmount}
            onChange={(e) => setMaxAmount(e.target.value)}
          />
        </div>
      </header>

      {paginatedOrders.length > 0 ? (
        <ul className="orders-list">
          {paginatedOrders.map((order) => (
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
                    <span>{p.name}</span>
                    <span>{p.quantity} ×</span>
                    <span>{p.price.toFixed(2)} €</span>
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
        <p className="empty-state">Няма резултати.</p>
      )}

      {/* 📄 PAGINATION CONTROLS */}
      {totalPages > 1 && (
        <div className="pagination">
          <button
            disabled={currentPage === 1}
            onClick={() => setCurrentPage((p) => p - 1)}
          >
            ◀
          </button>

          <span>
            Страница {currentPage} от {totalPages}
          </span>

          <button
            disabled={currentPage === totalPages}
            onClick={() => setCurrentPage((p) => p + 1)}
          >
            ▶
          </button>
        </div>
      )}
    </section>
  );
}
