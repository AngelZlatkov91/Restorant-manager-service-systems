import { Router } from 'express';
import orderController from './controllers/orderController.js'

const routes = Router();

routes.use('/api/order', orderController)



export default routes;