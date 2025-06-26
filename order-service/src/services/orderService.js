import Order from '../models/Order.js';
import OrderItem from '../models/OrderItem.js'


  const createOrder = async (tableNumber, items) =>{
    const order = await Order.create({tableNumber});
   
     const orderItems = Promise.all(items.map(item => 
        
        OrderItem.create({...item, orderId: order.id})
     ));
     order.dataValues.items = orderItems;
     return order;
    }

    const getTable = async (tableNum) => {
        return await OrderItem.findAll({where: {orderId: tableNum}});
    }

    const addItem = async (tableNumber, items) => {
        const table = await Order.findOne({where: {tableNumber: tableNumber}});
        const products = await getTable(tableNumber);

        products.forEach(product => {
            const productName = product.name;
            const productQuantity = product.quantity;

            items.forEach(item => {
                const currentName = item.name;
                if (currentName === productName) {
                    console.log('is include');
                    product.quantity = productQuantity + item.quantity;
                    console.log(product.quantity);
                    console.log(product);
                    product.update();
                } else {
                   const newItem = OrderItem.create({item, orderId: tableNumber})
                    table.dataValues.items = newItem;
                }
            })
        })

      return products;
    }



export default {
    createOrder,
    getTable,
    addItem,

};