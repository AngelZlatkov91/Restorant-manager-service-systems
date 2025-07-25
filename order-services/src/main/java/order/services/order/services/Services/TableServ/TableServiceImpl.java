package order.services.order.services.Services.TableServ;

import jakarta.transaction.Transactional;
import order.services.order.services.Models.DTO.CreateTable;
import order.services.order.services.Models.DTO.ResponseTable;
import order.services.order.services.Models.Entitys.TableEn;
import order.services.order.services.Repositories.TableRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableServiceImpl implements TableService {

    private final TableRepositories tableRepositories;

    public TableServiceImpl(TableRepositories tableRepositories) {
        this.tableRepositories = tableRepositories;
    }


    @Override
    public void createTable(CreateTable tableName) {
        TableEn newTableEn = new TableEn(tableName.getTableName(), true);
        tableRepositories.save(newTableEn);
    }

    @Override
    @Transactional
    public void dropTable(CreateTable tableName) {
     tableRepositories.removeByTableName(tableName.getTableName());
    }

    @Override
    @Transactional
    public void updateTable(ResponseTable responseTable) {
        Optional<TableEn> byName = tableRepositories.findById(responseTable.getId());
        if (byName.isPresent()) {
            byName.get().setTableName(responseTable.getTableName());
            tableRepositories.save(byName.get());
        }
    }

    @Override
    public List<ResponseTable> getTables() {
        return tableRepositories.findAll().stream().map(this::getCreateTable).collect(Collectors.toList());
    }

    private ResponseTable getCreateTable(TableEn tableEnName) {
        return new ResponseTable(tableEnName.getId(), tableEnName.getTableName());
    }
}
