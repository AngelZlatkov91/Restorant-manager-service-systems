package order.services.order.services.Services.PerssonalServ;

import order.services.order.services.Models.DTO.CheckPersonal;
import order.services.order.services.Models.DTO.CreatedPersonal;
import order.services.order.services.Models.DTO.DeleteProduct;
import order.services.order.services.Models.DTO.PersonalResponse;

public interface PersonalServices {

    void createPersonal(CreatedPersonal createdPersonal);

    void updatePersonal(CreatedPersonal createdPersonal);

    void deletePersonal(Long id);

    PersonalResponse getPersonal(Long id);

    void deleteProduct(DeleteProduct deleteProduct);

    String checkPersonal(CheckPersonal checkPersonal);

}
