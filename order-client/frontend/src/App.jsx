import { Route, Routes, Navigate } from "react-router-dom";
import LoginAuthWindow from "./components/LoginAuthWindow";
import { useEffect, useState } from "react";
import { getAccessToken } from "./utils/authUtils";
import TableSelection from "./components/table/TableSelection";
import OrderClient from "./components/order/OrderClient";
import Reports from "./components/reports/Reports";

function App() {
  const [isAuth, setIsAuth] = useState(null);

  useEffect(() => {
    (async () => {
      const token = await getAccessToken();
      setIsAuth(!!token);
    })();
  }, []);

  if (isAuth === null) {
    return <div>Loading...</div>;
  }

  return (
    <>
    
    <Routes>

      <Route path="/loginPage" element={isAuth ? <Navigate to="/tables" /> : <LoginAuthWindow />} />
      <Route path="*" element={<Navigate to={isAuth ? "/tables" : "/loginPage"} />} />
      <Route path="/tables" element={<TableSelection />} />

      <Route path="/orderClient/:id" element={<OrderClient />} />
      <Route path="/reports" element={<Reports />} />
    </Routes>
     </>

  );
}

export default App;
