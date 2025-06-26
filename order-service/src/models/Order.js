import { DataTypes } from 'sequelize';
import { sequelize } from '../config/db.js';
const Order = sequelize.define('Order', {
    tableNumber: { type: DataTypes.INTEGER, allowNull: false},
    status: {
        type: DataTypes.ENUM("RECEIVED", "COMPLETED", "CANCELLED"),
        defaultValue: "RECEIVED"
    }
}, {
    timestamps: true
});

export default Order;