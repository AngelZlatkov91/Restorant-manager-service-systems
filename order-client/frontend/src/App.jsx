import { Route, Routes, Navigate } from "react-router-dom";
import LoginAuthWindow from "./components/LoginAuthWindow";
import { useEffect, useState } from "react";
import { getAccessToken } from "./utils/authUtils";
import TableSelection from "./components/table/TableSelection";
import EmptyTable from "./components/table/EmptyTable";
import NotEmptyTable from "./components/table/NotEmptyTable"
import OrderClient from "./components/order/OrderClient";

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
    <Routes>

      <Route path="/loginPage" element={isAuth ? <Navigate to="/tables" /> : <LoginAuthWindow />} />
      <Route path="*" element={<Navigate to={isAuth ? "/tables" : "/loginPage"} />} />
      <Route path="/tables" element={<TableSelection />} />

      <Route path="/orderClient/:id" element={<OrderClient />} />
    </Routes>
  );
}

export default App;
