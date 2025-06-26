import { Router } from 'express';
import orderService from '../services/orderService.js'

const orderController = Router();

orderController.post('/create', async (req, res) => {
    const {tableNumber, items} = req.body;
    console.log(tableNumber)
    console.log(items)
    try {
    const createOrder = orderService.createOrder(tableNumber, items);
        return res.json(createOrder);
    } catch (err) {
        console.log("is nog created " + err)
    }

})

orderController.get('/:tableNum', async (req, res) => {
    const tableNumber = req.params.tableNum;
    
    const getProductInTable = await orderService.getTable(tableNumber);
    return res.json(getProductInTable)
})

orderController.post('/update/:tableNum', async (req, res) => {

    const tableNum = req.params.tableNum;
    const items = req.body;
   const newItems = orderService.addItem(tableNum, items);
   return res.json(newItems);
})

export default orderController;