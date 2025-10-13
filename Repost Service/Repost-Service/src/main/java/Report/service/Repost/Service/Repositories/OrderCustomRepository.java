package Report.service.Repost.Service.Repositories;

import Report.service.Repost.Service.Models.PersonalCostSummary;

import java.time.LocalDate;
import java.util.List;

public interface OrderCustomRepository {
    List<PersonalCostSummary> getTotalCostByPersonalNameAndDate(
            String paymentMethod, LocalDate startDate, LocalDate endDate);
}
