package Report.service.Repost.Service.Services;

import Report.service.Repost.Service.Event.DailyReportsDTO;
import Report.service.Repost.Service.Models.Reports;
import Report.service.Repost.Service.Repositories.ReportRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepositories reportRepositories;

    public ReportServiceImpl(ReportRepositories reportRepositories) {
        this.reportRepositories = reportRepositories;
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
        reportRepositories.save(reports);
    }
}
