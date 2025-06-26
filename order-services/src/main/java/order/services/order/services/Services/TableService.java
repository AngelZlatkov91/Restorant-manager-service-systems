package order.services.order.services.Services;
import order.services.order.services.Models.DTO.CreateTable;

import java.util.List;


public interface TableService {

    void createTable(String tableName);
    void dropTable(String tableName);

    void updateTable(String tableName, CreateTable createTable);

    List<CreateTable> getTables();




}
