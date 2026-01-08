package order.services.order.services.Services.PerssonalServ;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import order.services.order.services.Models.DTO.Personal.CheckPersonal;
import order.services.order.services.Models.DTO.Personal.CreatedPersonal;
import order.services.order.services.Models.DTO.Personal.PersonalResponse;
import order.services.order.services.Models.DTO.Personal.ResPersonalName;
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
        personal.setRole(createdPersonal.getRole());
        personal.setActive(true);
        personalRepositories.save(personal);
    }

    @Override
    @Transactional
    public void updatePersonal(PersonalResponse createdPersonal) {
        Optional<Personal> byPassword = personalRepositories.findByPassword(createdPersonal.getPassword());
        if ( byPassword.isEmpty()) {
            throw new EntityNotFoundException("Personal with name " + createdPersonal.getName() + " not found");
        }
        byPassword.get().setName(createdPersonal.getName());
        byPassword.get().setPassword(createdPersonal.getPassword());
        byPassword.get().setRole(createdPersonal.getRole());
        byPassword.get().setActive(createdPersonal.isActive());
        personalRepositories.save(byPassword.get());
    }

    @Override
    @Transactional
    public void changeIsActivePersonal(Long id) {
        Optional<Personal> byId = personalRepositories.findById(id);
        if (byId.get().isActive()) {
            byId.get().setActive(false);
        } else {
            byId.get().setActive(true);
        }

        personalRepositories.save(byId.get());
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
        personalResponse.setRole(personal.getRole());
        personalResponse.setPassword(personal.getPassword());
        personalResponse.setActive(personal.isActive());
        return personalResponse;
    }


    @Override
    public ResPersonalName checkPersonal(CheckPersonal checkPersonal) {
        Optional<Personal> byPassword = personalRepositories.findByPassword(checkPersonal.getPassword());
        if (byPassword.isEmpty() || !byPassword.get().isActive()) {
            throw new EntityNotFoundException("Personal with password " + checkPersonal.getPassword() + " not found");
        }
        return new ResPersonalName(byPassword.get().getName(),byPassword.get().getRole());
    }

    @Override
    public PersonalResponse getPersonalById(Long id) {
        Optional<Personal> byId = personalRepositories.findById(id);
        return mapPersonal(byId.get());
    }
}
