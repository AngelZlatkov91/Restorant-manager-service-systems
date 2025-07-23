package order.services.order.services.Services.TableServ;
import order.services.order.services.Models.DTO.CreateTable;
import order.services.order.services.Models.DTO.ResponseTable;

import java.util.List;


public interface TableService {

    void createTable(CreateTable tableName);
    void dropTable(CreateTable tableName);

    void updateTable(ResponseTable tableName);

    List<ResponseTable> getTables();




}
