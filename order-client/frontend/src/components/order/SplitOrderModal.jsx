import { useState, useEffect, use } from "react";
import { useGetAllTable } from "../../hooks/useTable";
import { useCreateOrder } from "../../hooks/useOrder";

export default function SplitOrderModal({ onClose, order, onRefresh }) {
  const [splits, setSplits] = useState(
    order.products.map(p => ({ ...p, transferQty: 0, selected: false }))
  );
  const [tables, fetchTables] = useGetAllTable(); 
  const [selectedTable, setSelectedTable] = useState("");
  console.log(tables);

  useEffect(() => {
    fetchTables(); 
  }, []);
 
  const freeTables = tables.filter(t => t.empty);

  const toggleSelect = (id) => {
    setSplits(splits.map(p => p.id === id ? { ...p, selected: !p.selected } : p));
  };

  const changeTransferQty = (id, qty) => {
    setSplits(splits.map(p => p.id === id ? { ...p, transferQty: qty } : p));
  };

  const handleSave = async () => {
    if (!selectedTable) {
      alert("Моля изберете маса за новата поръчка!");
      return;
    }

    // Избираме продуктите за прехвърляне
    const productsToTransfer = splits
      .filter(p => p.selected && p.transferQty > 0)
      .map(p => ({ ...p, quantity: p.transferQty }));

    if (productsToTransfer.length === 0) {
      alert("Не сте избрали продукти за прехвърляне!");
      return;
    }

    
    const remainingProducts = order.products.map(p => {
      const transfer = productsToTransfer.find(tp => tp.id === p.id);
      if (!transfer) return p;
      return { ...p, quantity: p.quantity - transfer.quantity };
    }).filter(p => p.quantity > 0);


    // Създаваме нова поръчка за избраната маса
    const newOrder = {
      id: selectedTable.id,
      personalName: order.personalName,
      products: productsToTransfer,
    };

    useCreateOrder(newOrder);

    onRefresh(); // обновяване на таблицата или текущата поръчка
    onClose();
  };

  return (
    <div style={modalStyle}>
      <h2>Раздели поръчка</h2>
      
      <div>
        {splits.map(p => (
          <div key={p.index} style={{ display: "flex", justifyContent: "space-between", marginBottom: "5px" }}>
            <label>
              <input type="checkbox" checked={p.selected} onChange={() => toggleSelect(p.id)} />
              {p.name} (Наличност: {p.quantity})
            </label>
            {p.selected && (
              <input
                type="number"
                min="1"
                max={p.quantity}
                value={p.transferQty}
                onChange={e => changeTransferQty(p.id, Number(e.target.value))}
                style={{ width: "60px" }}
              />
            )}
          </div>
        ))}
      </div>

      <div style={{ marginTop: "10px" }}>
        <label>Изберете маса за новата поръчка: </label>
        <select value={selectedTable} onChange={e => setSelectedTable(e.target.value)}>
          <option value="">-- Изберете маса --</option>
          {freeTables.map(t => (
            <option key={t.id} value={t.id}>{t.tableName}</option>
          ))}
        </select>
      </div>

      <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
        <button onClick={handleSave} style={{ padding: "6px 12px", background: "#4caf50", color: "white" }}>Запази</button>
        <button onClick={onClose} style={{ padding: "6px 12px" }}>Отказ</button>
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
