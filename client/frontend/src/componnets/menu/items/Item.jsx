import { useState, useEffect } from "react";
import { useDeleteMenuItem, useGetAllMenuItems } from "../../../hooks/useItem";
import ConfirmPopup from "../../confirmModal/ConfirmPop";
import { useNavigate } from "react-router-dom";

export default function Item() {
  const navigate = useNavigate();
  const [items, fetchItems] = useGetAllMenuItems();
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);

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

  const stockColor = (status) => {
    switch(status) {
      case 'AVAILABLE': return 'green';
      case 'LOW': return 'orange';
      case 'OUT_OF_STOCK': return 'red';
      default: return 'black';
    }
  };

  const calculateMarkup = (price, costPrice) => {
    if (!costPrice || costPrice === 0) return 0;
    return (((price - costPrice) / costPrice) * 100).toFixed(2);
  };

  return (
    <section className="items-page">
      <header className="items-header">
        <h2>Меню артикули</h2>
        <button className="btn btn-primary" onClick={() => navigate("/createItem")}>
          ➕ Създай артикул
        </button>
      </header>

      {items && items.length > 0 ? (
        <ul className="items-list">
          {items.map((item) => (
            <li key={item.id} className="item-card">
              <div className="item-info">
                <strong className={item.active ? "" : "inactive"}>
                  {item.name}
                </strong>

                <div className="item-meta">
                  <span title={`Cost: ${item.costPrice} | Markup: ${item.markupPercentage || calculateMarkup(item.price, item.costPrice)}%`}>
                    💰 {item.price} лв.
                  </span>
                  <span>🏷️ {item.category || "Няма"}</span>
                  <span>🍽️ {item.typeProduct}</span>
                  <span>📅 Създадено: {new Date(item.createdAt).toLocaleString()}</span>
                  <span>✏️ Последна промяна: {new Date(item.updatedAt).toLocaleString()}</span>
                  {item.costPrice && <span>💲 Покупна цена: {item.costPrice} лв.</span>}
                  {item.markupPercentage && <span>⬆ Надценка: {item.markupPercentage}%</span>}
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
        <p className="empty-state">Няма налични артикули.</p>
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
