import { DataTypes } from "sequelize";
import { sequelize } from "../config/db.js";
import Order from "./Order.js";

const OrderItem = sequelize.define("OrderItem", {
    menuItemId: {type: DataTypes.STRING, allowNull: false},
    name: {type: DataTypes.STRING,allowNull: false},
    quantity: {type: DataTypes.INTEGER, allowNull: false},
    price: {type: DataTypes.FLOAT, allowNull: false}
}, {
    timestamps: false
});

Order.hasMany(OrderItem, {as: "items", foreignKey: "orderId"});
OrderItem.belongsTo(Order, { foreignKey: "orderId"});

export default OrderItem;