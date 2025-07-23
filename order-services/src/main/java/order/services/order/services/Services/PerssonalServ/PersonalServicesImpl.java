package order.services.order.services.Services.PerssonalServ;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import order.services.order.services.Models.DTO.CheckPersonal;
import order.services.order.services.Models.DTO.CreatedPersonal;
import order.services.order.services.Models.DTO.DeleteProduct;
import order.services.order.services.Models.DTO.PersonalResponse;
import order.services.order.services.Models.Entitys.Personal;
import order.services.order.services.Repositories.OrderRepositories;
import order.services.order.services.Repositories.PersonalRepositories;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PersonalServicesImpl implements PersonalServices {
    private final PersonalRepositories personalRepositories;

    private final OrderRepositories orderRepositories;

    public PersonalServicesImpl(PersonalRepositories personalRepositories, OrderRepositories orderRepositories) {
        this.personalRepositories = personalRepositories;
        this.orderRepositories = orderRepositories;
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
    public void updatePersonal(CreatedPersonal createdPersonal) {
        Optional<Personal> personal = personalRepositories.findByName(createdPersonal.getName());
        if (personal.isEmpty()) {
            throw new EntityNotFoundException("Personal with name " + createdPersonal.getName() + " not found");
        }
        personal.get().setName(createdPersonal.getName());
        personal.get().setPassword(createdPersonal.getPassword());
        personalRepositories.save(personal.get());

    }

    @Override
    public void deletePersonal(Long id) {
       personalRepositories.deleteById(id);
    }

    @Override
    public PersonalResponse getPersonal(Long id) {
        Optional<Personal> personal = personalRepositories.findById(id);
        if (personal.isEmpty()) {
            throw new EntityNotFoundException("Personal with id " + id + " not found");
        }
        PersonalResponse personalResponse = new PersonalResponse();
        personalResponse.setName(personal.get().getName());
        personalResponse.setPassword(personal.get().getPassword());
        personalResponse.setId(personal.get().getId());

        return personalResponse;
    }

    @Override
    @Transactional
    public void deleteProduct(DeleteProduct deleteProduct) {
        orderRepositories.findById(deleteProduct.getOrderId()).ifPresent(order -> {
            order.getProducts().remove(deleteProduct.getIndexProduct());
        });
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
