import dotenv from 'dotenv';
dotenv.config();
import express from 'express';

import routes from './router.js';

import { connectDB, sequelize } from './config/db.js';

const app = express();

app.use(express.json());

connectDB();

sequelize.sync({alter: true}).then(() => {
    console.log("database synchronized")
})

app.use(routes);

app.listen(3030, () => console.log('Server is listening on http://localhost:3030'));
