package order.services.order.services.Services.PerssonalServ;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import order.services.order.services.Models.DTO.Personal.CheckPersonal;
import order.services.order.services.Models.DTO.Personal.CreatedPersonal;
import order.services.order.services.Models.DTO.Personal.PersonalResponse;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Repositories.PersonalRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalServicesImpl implements PersonalServices {
    private final PersonalRepositories personalRepositories;


    public PersonalServicesImpl(PersonalRepositories personalRepositories) {
        this.personalRepositories = personalRepositories;
    }

    @Override
    public void createPersonal(CreatedPersonal createdPersonal) {
        Personal personal = new Personal();
        personal.setName(createdPersonal.getName());
        personal.setPassword(createdPersonal.getPassword());
        personalRepositories.save(personal);
    }

    @Override
    @Transactional
    public void updatePersonal(Long id ,PersonalResponse createdPersonal) {
        Optional<Personal> byId = personalRepositories.findById(id);
        Optional<Personal> byPassword = personalRepositories.findByPassword(createdPersonal.getPassword());
        if (byId.isEmpty() ||  byPassword.isPresent()) {
            throw new EntityNotFoundException("Personal with name " + createdPersonal.getName() + " not found");
        }
        byId.get().setName(createdPersonal.getName());
        byId.get().setPassword(createdPersonal.getPassword());
        personalRepositories.save(byId.get());
    }

    @Override
    public void deletePersonal(Long id) {
       personalRepositories.deleteById(id);
    }

    @Override
    public List<PersonalResponse> getPersonal() {
        return personalRepositories
                .findAll()
                .stream()
                .map(this::mapPersonal)
                .toList();
    }

    private PersonalResponse mapPersonal(Personal personal) {
        PersonalResponse personalResponse = new PersonalResponse();
        personalResponse.setId(personal.getId());
        personalResponse.setName(personal.getName());
        personalResponse.setPassword(personal.getPassword());
        return personalResponse;
    }


    @Override
    public String checkPersonal(CheckPersonal checkPersonal) {
        Optional<Personal> byPassword = personalRepositories.findByPassword(checkPersonal.getPassword());
        if (byPassword.isEmpty()) {
            throw new EntityNotFoundException("Personal with password " + checkPersonal.getPassword() + " not found");
        }
        return byPassword.get().getName();
    }
}
