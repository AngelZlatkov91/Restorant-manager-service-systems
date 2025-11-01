import { useInventoryItems } from "../../hooks/useInventory";

export default function Inventory() {
const [items, fetchItems] = useInventoryItems();
console.log(items);


   return (
        <section id="items" style={{ padding: "20px" }}>
              <h2>Меню артикули</h2>
              {items && items.length > 0 ? (
                <ul style={{ listStyle: "none", padding: 0 }}>
                  {items.map((item) => (
                    <li
                      key={item.id} // ✅ уникален key
                      style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        background: "#f9f9f9",
                        marginBottom: "10px",
                        padding: "10px 15px",
                        borderRadius: "8px",
                        boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
                      }}
                    >
                      <div>
                        <strong>{item.name}</strong>
                        <p style={{ margin: "5px 0" }}>
                          💰 Цена: {item.price} лв. | 🏷️ Quantity: {item.quantity || "Няма"} 
                        </p>
                      </div>
        
                      <div style={{ display: "flex", gap: "10px" }}>
                        <button
                          
                          style={{
                            padding: "6px 12px",
                            border: "none",
                            borderRadius: "5px",
                            background: "#4caf50",
                            color: "white",
                            cursor: "pointer",
                          }}
                        >
                          Edit
                        </button>
        
                        <button
                          
                          style={{
                            padding: "6px 12px",
                            border: "none",
                            borderRadius: "5px",
                            background: "#f44336",
                            color: "white",
                            cursor: "pointer",
                          }}
                        >
                          Delete
                        </button>
        
                      </div>
                    </li>
                  ))}
                </ul>
              ) : (
                <p>Няма налични артикули.</p>
              )}
        
              
            </section>
     
  );
}