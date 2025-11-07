import { useNavigate } from "react-router-dom";

export default function TableSelection() {
  const navigate = useNavigate();

  const tables = Array.from({ length: 16 }, (_, i) => ({
    id: i + 1,
    status: Math.random() > 0.5 ? "free" : "busy",
  }));

  const handleTableClick = (tableId) => {
    navigate(`/orders/create?table=${tableId}`);
  };

  return (
    <div className="min-h-screen bg-gray-100 p-10">
      <h1 className="text-4xl font-bold text-center mb-8">Select a Table</h1>
      <div className="grid grid-cols-4 gap-6 max-w-4xl mx-auto">
        {tables.map((table) => (
          <div
            key={table.id}
            onClick={() => handleTableClick(table.id)}
            className={`cursor-pointer p-8 rounded-2xl shadow-md text-center text-xl font-semibold transition-all duration-200 hover:scale-105 ${
              table.status === "free"
                ? "bg-green-300 hover:bg-green-400"
                : "bg-red-300 hover:bg-red-400"
            }`}
          >
            Table {table.id}
          </div>
        ))}
      </div>
    </div>
  );
}
