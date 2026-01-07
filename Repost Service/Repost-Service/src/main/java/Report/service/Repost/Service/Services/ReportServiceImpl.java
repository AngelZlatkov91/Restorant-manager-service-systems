package Report.service.Repost.Service.Services;

import Report.service.Repost.Service.Models.DailyReport;
import Report.service.Repost.Service.Models.ReportDTO;
import Report.service.Repost.Service.Repositories.DailyReportRepositories;
import order.repost.DailyReportsDTO;
import Report.service.Repost.Service.Models.PersonalCostSummary;
import Report.service.Repost.Service.Models.Reports;
import Report.service.Repost.Service.Repositories.OrderCustomRepository;
import Report.service.Repost.Service.Repositories.ReportRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepositories reportRepositories;
    private final OrderCustomRepository orderCustomRepository;
    private final DailyReportRepositories dailyReportRepositories;

    public ReportServiceImpl(ReportRepositories reportRepositories, OrderCustomRepository orderCustomRepository, DailyReportRepositories dailyReportRepositories) {
        this.reportRepositories = reportRepositories;
        this.orderCustomRepository = orderCustomRepository;
        this.dailyReportRepositories = dailyReportRepositories;
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
        addReportToPersonal(dailyReportsDTO.getPersonalName(),dailyReportsDTO.getTotalCost());

    }

    private void addReportToPersonal(String personalName, Double totalCost) {
        Optional<DailyReport> byPersonalName = dailyReportRepositories.findByPersonalName(personalName);
        if (byPersonalName.isEmpty()) {
            DailyReport dailyReport = new DailyReport();
            dailyReport.setDailyReport(totalCost);
            dailyReport.setPersonalName(personalName);
            dailyReport.setCheck(false);
            dailyReportRepositories.save(dailyReport);
        } else  {
            Double dailyReport = byPersonalName.get().getDailyReport();
            byPersonalName.get().setDailyReport(dailyReport + totalCost);
            byPersonalName.get().setCheck(false);
            dailyReportRepositories.save(byPersonalName.get());
        }
    }


    public List<PersonalCostSummary> getPersonalReport(){
       return orderCustomRepository.getTotalCostByPersonalNameAndDate("CASH",LocalDate.now(),LocalDate.now());

//       return null;
    }

    @Override
    public List<ReportDTO> allReports() {
        List<Reports> all = reportRepositories.findAll();
        return all.stream().map(this::mapReports).toList();
    }

    private ReportDTO mapReports(Reports reports) {
        return  new ReportDTO(
                reports.getId(),
                reports.getPersonalName(),
                reports.getOrderId(),
                reports.getCompletedIn(),
                reports.getTotalCost(),
                reports.getPaymentMethod(),
                reports.getCreated(),
                reports.isCheck()
        );
    }
}
