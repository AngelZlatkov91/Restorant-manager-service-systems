package order.services.order.services.Api;

import jakarta.validation.Valid;
import order.services.order.services.Models.DTO.CreateTable;
import order.services.order.services.Models.DTO.ResponseTable;
import order.services.order.services.Services.TableServ.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTable(@RequestBody @Valid CreateTable createTable, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            String defaultMessage = allErrors.get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(defaultMessage);
        }
        tableService.createTable(createTable);
        return new ResponseEntity<>("Table created", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTable(@RequestBody CreateTable createTable) {
        tableService.dropTable(createTable);
        return new ResponseEntity<>("Table dropped", HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateTable(@RequestBody ResponseTable responseTable) {
        tableService.updateTable(responseTable);
        return new ResponseEntity<>("Table updated", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTable>> getTables() {
        return new ResponseEntity<>(tableService.getTables(), HttpStatus.OK);
    }



}
