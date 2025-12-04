package Report.service.Repost.Service.Services;

import Report.service.Repost.Service.Models.PersonalCostSummary;
import order.repost.DailyReportsDTO;

import java.util.List;

public interface ReportService {
    void addReport(DailyReportsDTO dailyReportsDTO);
    List<PersonalCostSummary> getPersonalReport();


}
