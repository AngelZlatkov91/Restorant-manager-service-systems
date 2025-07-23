package order.services.order.services.Api;

import order.services.order.services.Models.DTO.CheckPersonal;
import order.services.order.services.Services.PerssonalServ.PersonalServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/check")
public class PersonalChekController {

    private final PersonalServices personalServices;

    public PersonalChekController(PersonalServices personalServices) {
        this.personalServices = personalServices;
    }

    @PostMapping
    public ResponseEntity<String> getPersonalChek(@RequestBody CheckPersonal checkPersonal) {
        return ResponseEntity.ok(personalServices.checkPersonal(checkPersonal));
    }
}
