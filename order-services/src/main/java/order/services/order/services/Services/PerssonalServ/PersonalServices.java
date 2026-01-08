package order.services.order.services.Services.PerssonalServ;

import order.services.order.services.Models.DTO.Personal.CheckPersonal;
import order.services.order.services.Models.DTO.Personal.CreatedPersonal;
import order.services.order.services.Models.DTO.Personal.PersonalResponse;
import order.services.order.services.Models.DTO.Personal.ResPersonalName;

import java.util.List;

public interface PersonalServices {

    void createPersonal(CreatedPersonal createdPersonal);

    void updatePersonal(PersonalResponse createdPersonal);

    void changeIsActivePersonal(Long id);

    List<PersonalResponse> getPersonal();


    ResPersonalName checkPersonal(CheckPersonal checkPersonal);

    PersonalResponse getPersonalById(Long id);
}
