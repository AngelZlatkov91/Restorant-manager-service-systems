package Report.service.Repost.Service.Services;

import Report.service.Repost.Service.Event.SendToPrintReport.DisplayEvent;
import Report.service.Repost.Service.Event.SendToPrintReport.ReportForDay;
import Report.service.Repost.Service.Models.DailyReport;
import Report.service.Repost.Service.Models.DailyReportDTO;
import Report.service.Repost.Service.Models.ResPersonalName;
import Report.service.Repost.Service.Repositories.DailyReportRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DailyReportsServiceImpl implements DailyReportsService {

    private final DailyReportRepositories dailyReportRepositories;
    private final DisplayEvent displayEvent;
    public DailyReportsServiceImpl(DailyReportRepositories dailyReportRepositories, DisplayEvent displayEvent) {
        this.dailyReportRepositories = dailyReportRepositories;
        this.displayEvent = displayEvent;
    }

    @Override
    public DailyReportDTO getReport(ResPersonalName personalName) {
        Optional<DailyReport> byPersonalName = dailyReportRepositories.findByPersonalName(personalName.getPersonalName());
        if (byPersonalName.isEmpty() || byPersonalName.get().isCheck()) {
            return null;
        }
        return new DailyReportDTO(byPersonalName.get().getId(),byPersonalName.get().getPersonalName(),byPersonalName.get().getDailyReport());
    }

    @Override
    @Transactional
    public void isChek(String id) {
        Optional<DailyReport> byPersonalName = dailyReportRepositories.findById(id);
        displayEvent.printDailyReport(new ReportForDay(byPersonalName.get().getPersonalName(), LocalDateTime.now(),byPersonalName.get().getDailyReport()));
        byPersonalName.get().setCheck(true);
        byPersonalName.get().setDailyReport(0.0);
        dailyReportRepositories.save(byPersonalName.get());

    }
}
