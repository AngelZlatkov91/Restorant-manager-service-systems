import { useState, useEffect, useMemo } from "react";
import { useDeleteMenuItem, useGetAllMenuItems } from "../../../hooks/useItem";
import ConfirmPopup from "../../confirmModal/ConfirmPop";
import { useNavigate } from "react-router-dom";


export default function Item() {
  const navigate = useNavigate();
  const [items, fetchItems] = useGetAllMenuItems();

  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);

 
  const [filters, setFilters] = useState({
    name: "",
    category: "",
    typeProduct: "",
  });

  useEffect(() => {
    fetchItems();
  }, []);

  const handleDeleteClick = (item) => {
    setItemToDelete(item);
    setIsConfirmOpen(true);
  };

  const handleConfirm = async () => {
    if (!itemToDelete) return;
    await useDeleteMenuItem(itemToDelete.id);
    await fetchItems();
    setIsConfirmOpen(false);
    setItemToDelete(null);
  };

  const handleCancel = () => {
    setIsConfirmOpen(false);
    setItemToDelete(null);
  };

  
  const filteredItems = useMemo(() => {
    return items?.filter((item) => {
      const matchName =
        item.name.toLowerCase().includes(filters.name.toLowerCase());

      const matchCategory =
        !filters.category || item.category === filters.category;

      const matchType =
        !filters.typeProduct || item.typeProduct === filters.typeProduct;

      return matchName && matchCategory && matchType;
    });
  }, [items, filters]);

  const uniqueCategories = [...new Set(items?.map(i => i.category).filter(Boolean))];
  const uniqueTypes = [...new Set(items?.map(i => i.typeProduct).filter(Boolean))];

  
  const calculateMarkup = (price, costPrice) => {
    if (!costPrice || costPrice === 0) return 0;
    return (((price - costPrice) / costPrice) * 100).toFixed(2);
  };

  const stockColor = (status) => {
    switch(status) {
      case 'AVAILABLE': return 'green';
      case 'LOW': return 'orange';
      case 'OUT_OF_STOCK': return 'red';
      default: return 'black';
    }
  };

  return (
    <section className="items-page">
      <header className="items-header">
        <h2>Меню артикули</h2>
        <button className="btn btn-primary" onClick={() => navigate("/createItem")}>
          ➕ Създай артикул
        </button>
      </header>

     
      <div className="filter-bar">
        <input
          type="text"
          placeholder="Търси по име..."
          value={filters.name}
          onChange={(e) => setFilters((s) => ({ ...s, name: e.target.value }))}
        />

        <select
          value={filters.category}
          onChange={(e) => setFilters((s) => ({ ...s, category: e.target.value }))}
        >
          <option value="">Всички категории</option>
          {uniqueCategories.map((c) => <option key={c} value={c}>{c}</option>)}
        </select>

        <select
          value={filters.typeProduct}
          onChange={(e) => setFilters((s) => ({ ...s, typeProduct: e.target.value }))}
        >
          <option value="">Всички типове</option>
          {uniqueTypes.map((t) => <option key={t} value={t}>{t}</option>)}
        </select>
      </div>

      {filteredItems && filteredItems.length > 0 ? (
        <ul className="items-list">
          {filteredItems.map((item) => (
            <li key={item.id} className="item-card">
              <div className="item-info">
                <strong className={item.active ? "" : "inactive"}>
                  {item.name}
                </strong>

                <div className="item-meta">
                  <span>💰 Цена: {item.price} лв.</span>
                  {item.costPrice && <span>💲 Покупна: {item.costPrice} лв.</span>}
                  <span>⬆ Надценка: {item.markupPercentage ?? calculateMarkup(item.price, item.costPrice)}%</span>
                  <span>🏷️ Категория: {item.category || "Няма"}</span>
                  <span>🍽 Тип: {item.typeProduct}</span>
                  <span>📅 Създадено: {new Date(item.createdAt).toLocaleString()}</span>
                  <span>✏️ Последна промяна: {new Date(item.updatedAt).toLocaleString()}</span>
                  <span style={{ color: stockColor(item.status) }}>📦 {item.status}</span>
                </div>
              </div>

              <div className="item-actions">
                <button
                  className="btn btn-success"
                  onClick={() => navigate(`/editItem/${item.id}`)}
                  title="Редактирай"
                >
                  ✏️
                </button>

                <button
                  className="btn btn-danger"
                  onClick={() => handleDeleteClick(item)}
                  title="Изтрий"
                >
                  🗑
                </button>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className="empty-state">Няма резултати.</p>
      )}

      <ConfirmPopup
        isOpen={isConfirmOpen}
        message={`Сигурен ли си, че искаш да изтриеш "${itemToDelete?.name}"?`}
        onConfirm={handleConfirm}
        onCancel={handleCancel}
      />
    </section>
  );
}
