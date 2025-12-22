import { useState, useEffect } from "react";

import { fetchOrders } from "../api/order-api";

export function useOrder() {
  const [orders, setOrders] = useState([]);
  
  const loadOrders = async () => {
     const data = await fetchOrders();
        setOrders(data);
  };
  useEffect(() => {
    loadOrders();
  }, []);

  return [orders, loadOrders];

}