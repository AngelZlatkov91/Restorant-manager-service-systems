package Report.service.Repost.Service.Services;

import Report.service.Repost.Service.Models.DailyReportDTO;
import Report.service.Repost.Service.Models.ResPersonalName;

public interface DailyReportsService {
    DailyReportDTO getReport(ResPersonalName personalId);
    void isChek(String id);
}
