import { useState,useEffect } from "react";

import orderApi from "../api/order";



export function useGetActiveOrder(id) {
  const [activeOrder, setActiveOrder] = useState(
     {
    id: '',
    table_name: '',
    personal_name: '',
    status: '',
    products: [{
    name: '',
    category: null,
    price: '',
    quantity: '',
    description: '',
    check: Boolean
    }]
}
  );
    useEffect(() =>  {
      (async () => {
        const result = await orderApi.getOrder(id);
      setActiveOrder(result);
      })();
      
    },[id])
       
          return [activeOrder, setActiveOrder];
}

export function useCreateOrder(data) {
  const orderCreate = orderApi.createOrder(data);
  return orderCreate;
}

export function useUpdateOrder(data) {
  const orderUpdate = orderApi.updateOrder(data);
  return orderUpdate;
}