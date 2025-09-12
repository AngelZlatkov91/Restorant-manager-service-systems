package order.services.order.services.Services.PerssonalServ;

import order.services.order.services.Models.DTO.Personal.CheckPersonal;
import order.services.order.services.Models.DTO.Personal.CreatedPersonal;
import order.services.order.services.Models.DTO.Personal.PersonalResponse;

import java.util.List;

public interface PersonalServices {

    void createPersonal(CreatedPersonal createdPersonal);

    void updatePersonal(Long id,PersonalResponse createdPersonal);

    void deletePersonal(Long id);

    List<PersonalResponse> getPersonal();


    String checkPersonal(CheckPersonal checkPersonal);

}
