package order.services.order.services.Event.Reports;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import order.repost.DailyReportsDTO;
@Component
@Slf4j
public class DailyReportsEvent {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public DailyReportsEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendEvent(DailyReportsDTO event) {
        kafkaTemplate.send("daily-reports-service", event);
    }
}
