package Report.service.Repost.Service.Event.SendToPrintReport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DisplayEvent {
    private final KafkaTemplate<String, ReportForDay> kafkaTemplate;
     @Autowired
    public DisplayEvent(KafkaTemplate<String, ReportForDay> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void printDailyReport(ReportForDay reports) {
        kafkaTemplate.send("daily-report", reports);
    }
}
