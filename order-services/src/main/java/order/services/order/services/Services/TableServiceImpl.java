package order.services.order.services.Services;

import jakarta.transaction.Transactional;
import order.services.order.services.Models.DTO.CreateTable;
import order.services.order.services.Models.DTO.ResponseTable;
import order.services.order.services.Models.Entitys.Table;
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
        Table newTable = new Table(tableName.getTableName(), true);
        tableRepositories.save(newTable);
    }

    @Override
    @Transactional
    public void dropTable(CreateTable tableName) {
        Optional<Table> byTableName = tableRepositories.findByTableName(tableName.getTableName());
        tableRepositories.delete(byTableName.get());
    }

    @Override
    @Transactional
    public void updateTable(ResponseTable responseTable) {
        Optional<Table> byName = tableRepositories.findById(responseTable.getId());
        if (byName.isPresent()) {
            byName.get().setTableName(responseTable.getTableName());
            tableRepositories.save(byName.get());
        }
    }

    @Override
    public List<ResponseTable> getTables() {
        return tableRepositories.findAll().stream().map(this::getCreateTable).collect(Collectors.toList());
    }

    private ResponseTable getCreateTable(Table tableName) {
        return new ResponseTable(tableName.getId(),tableName.getTableName());
    }
}
