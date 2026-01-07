package Report.service.Repost.Service.Services;

import Report.service.Repost.Service.Models.PersonalCostSummary;
import Report.service.Repost.Service.Models.ReportDTO;
import order.repost.DailyReportsDTO;

import java.util.List;

public interface ReportService {
    void addReport(DailyReportsDTO dailyReportsDTO);
    List<PersonalCostSummary> getPersonalReport();
    List<ReportDTO> allReports();



}
