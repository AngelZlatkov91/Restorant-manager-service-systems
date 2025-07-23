package order.services.order.services.Api;
import jakarta.validation.Valid;
import order.services.order.services.Models.DTO.CreatedPersonal;
import order.services.order.services.Models.DTO.DeleteProduct;
import order.services.order.services.Models.DTO.PersonalResponse;
import order.services.order.services.Services.PerssonalServ.PersonalServices;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody CreatedPersonal personal) {
        personalServices.updatePersonal(personal);
        return ResponseEntity.ok("Personal updated");
    }

    @DeleteMapping("/remove/:id")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        personalServices.deletePersonal(id);
        return ResponseEntity.ok("Personal deleted");
    }

    @GetMapping("/:id")
    public ResponseEntity<PersonalResponse> findById(@RequestParam Long id) {
        return ResponseEntity.ok(personalServices.getPersonal(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestBody DeleteProduct deleteProduct) {
        personalServices.deleteProduct(deleteProduct);
        return ResponseEntity.ok("Product deleted");
    }


}
