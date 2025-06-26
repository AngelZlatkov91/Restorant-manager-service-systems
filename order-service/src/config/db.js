import dotenv from 'dotenv';
dotenv.config();
import { Sequelize } from 'sequelize';

export const sequelize = new Sequelize(
  process.env.MYSQL_DATABASE,
  process.env.MYSQL_USER,
  process.env.MYSQL_PASSWORD, 
  {
    host: process.env.MYSQL_HOST,
    dialect: 'mysql',
    query: (`CREATE DATABASE IF NOT EXISTS \`${process.env.MYSQL_DATABASE}\`;`),
   
  }
);


export const connectDB = async () => {
  try {
    await sequelize.authenticate();
   
    console.log("✅ MySQL connected");
  } catch (err) {
    console.error("❌ Connection error:", err);
  }
};