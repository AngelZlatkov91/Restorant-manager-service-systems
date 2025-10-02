package Report.service.Repost.Service.Event;

import Report.service.Repost.Service.Services.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerEvent {
    private final ReportService reportService;

    public ConsumerEvent(ReportService reportService) {
        this.reportService = reportService;
    }


    @KafkaListener(topics = "daily-reports-service", groupId = "report-service")
    public void dailyReport(DailyReportsDTO dailyReportsDTO) {
        reportService.addReport(dailyReportsDTO);
    }
}
