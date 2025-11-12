import { useState,useEffect } from "react";

import orderApi from "../api/order";



export function useGetAllActiveOrder(id) {
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