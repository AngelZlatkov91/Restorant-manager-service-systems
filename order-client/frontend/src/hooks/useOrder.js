import { useState,useEffect } from "react";

import orderApi from "../api/order";



export function useGetAllActiveOrder(id) {
  const [activeOrder, setActiveOrder] = useState([]);
    const fetchActiveOrder = async () => {
      const result = orderApi.getOrder(id);
      setActiveOrder(result);
    };
    useEffect(() => {
      fetchActiveOrder();
      
    },[])
         
          return [activeOrder, fetchActiveOrder];
}