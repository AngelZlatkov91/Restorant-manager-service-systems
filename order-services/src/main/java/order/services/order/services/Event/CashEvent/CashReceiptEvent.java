package order.services.order.services.Event.CashEvent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import receipt.CashReceiptDTO;

@Component
@Slf4j
public class CashReceiptEvent {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    public CashReceiptEvent(KafkaTemplate<String,Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void printReceipt(CashReceiptDTO event) {
        kafkaTemplate.send("print-receipt", event);

    }

    public void printTable(CashReceiptDTO event) {
        kafkaTemplate.send("print-table", event);
    }
}
