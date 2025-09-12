package order.services.order.services.Api;

import order.services.order.services.Models.DTO.Personal.CheckPersonal;
import order.services.order.services.Models.DTO.Table.ResponseTable;
import order.services.order.services.Services.PerssonalServ.PersonalServices;
import order.services.order.services.Services.TableServ.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/check")
public class ChekController {

    private final PersonalServices personalServices;
    private final TableService tableService;

    public ChekController(PersonalServices personalServices, TableService tableService) {
        this.personalServices = personalServices;
        this.tableService = tableService;
    }

    @PostMapping("/personal")
    public ResponseEntity<String> getPersonalChek(@RequestBody CheckPersonal checkPersonal) {
        return ResponseEntity.ok(personalServices.checkPersonal(checkPersonal));
    }


    @GetMapping("/table")
    public ResponseEntity<List<ResponseTable>> getTables() {
        return new ResponseEntity<>(tableService.getTables(), HttpStatus.OK);
    }

}
