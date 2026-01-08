package order.services.order.services.Services.TableServ;
import order.services.order.services.Models.DTO.Table.CreateTable;
import order.services.order.services.Models.DTO.Table.ResponseTable;

import java.util.List;


public interface TableService {

    void createTable(CreateTable tableName);
    void dropTable(Long id);

    void updateTable(Long id,ResponseTable tableName);

    List<ResponseTable> getTables();

    ResponseTable getById(Long id);




}
