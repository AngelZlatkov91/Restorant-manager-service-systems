import { useNavigate } from "react-router-dom";
import { useInventoryItems } from "../../hooks/useInventory";

export default function Inventory() {
  const navigate = useNavigate();
  const [items, fetchItems] = useInventoryItems();

  const handleEdit = (id) => {
    navigate(`/inventory/${id}`);
  };


  return (
    <section className="inventory-page">
      <header className="inventory-header">
        <h2>Инвентар</h2>
        
      </header>

      {items && items.length > 0 ? (
        <ul className="inventory-list">
          {items.map((item) => (
            <li key={item.id} className="inventory-card">
              <div className="inventory-info">
                <strong>{item.name}</strong>
                <p>
                  💰 Цена: {item.price} лв. | 🏷️ Количество: {item.quantity || "Няма"}
                </p>
              </div>

              <div className="inventory-actions">
                <button className="btn btn-success" onClick={() => handleEdit(item.id)}>
                  ✏️ Edit
                </button>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className="empty-state">Няма налични артикули.</p>
      )}
    </section>
  );
}
