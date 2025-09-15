package order.services.order.services.Api;
import jakarta.validation.Valid;
import order.services.order.services.Models.DTO.Personal.CreatedPersonal;
import order.services.order.services.Models.DTO.Personal.PersonalResponse;
import order.services.order.services.Services.PerssonalServ.PersonalServices;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal")
public class PersonalController {
    private final PersonalServices personalServices;

    public PersonalController(PersonalServices personalServices) {
        this.personalServices = personalServices;
    }


    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid CreatedPersonal personal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        personalServices.createPersonal(personal);
        return ResponseEntity.ok("Personal created");
    }

    @PutMapping("/update/:id")
    public ResponseEntity<String> update(@RequestParam Long id, @RequestBody PersonalResponse personal) {
        personalServices.updatePersonal(id,personal);
        return ResponseEntity.ok("Personal updated");
    }

    @DeleteMapping("/remove/:id")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        personalServices.deletePersonal(id);
        return ResponseEntity.ok("Personal deleted");
    }

    @GetMapping()
    public ResponseEntity<List<PersonalResponse>> findById() {
        return ResponseEntity.ok(personalServices.getPersonal());
    }
}
