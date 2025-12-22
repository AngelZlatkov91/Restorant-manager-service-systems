package Report.service.Repost.Service.Services;

import order.repost.DailyReportsDTO;
import Report.service.Repost.Service.Models.PersonalCostSummary;
import Report.service.Repost.Service.Models.Reports;
import Report.service.Repost.Service.Repositories.OrderCustomRepository;
import Report.service.Repost.Service.Repositories.ReportRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepositories reportRepositories;
    private final OrderCustomRepository orderCustomRepository;

    public ReportServiceImpl(ReportRepositories reportRepositories, OrderCustomRepository orderCustomRepository) {
        this.reportRepositories = reportRepositories;
        this.orderCustomRepository = orderCustomRepository;
    }

    @Override
    public void addReport(DailyReportsDTO dailyReportsDTO) {
        Reports reports = new Reports();
        reports.setCompletedIn(dailyReportsDTO.getCompletedIn());
        reports.setCreated(LocalDate.now());
        reports.setOrderId(dailyReportsDTO.getOrderId());
        reports.setPaymentMethod(dailyReportsDTO.getPaymentMethod());
        reports.setPersonalName(dailyReportsDTO.getPersonalName());
        reports.setTotalCost(dailyReportsDTO.getTotalCost());
        reports.setCheck(false);
        reportRepositories.save(reports);
    }


    public List<PersonalCostSummary> getPersonalReport(){
       return orderCustomRepository.getTotalCostByPersonalNameAndDate("CASH",LocalDate.now(),LocalDate.now());

//       return null;
    }
}
