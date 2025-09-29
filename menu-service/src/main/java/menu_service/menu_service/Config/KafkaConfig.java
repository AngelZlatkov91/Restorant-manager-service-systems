package menu_service.menu_service.Config;

import menu_service.menu_service.Event.InventoryDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaTemplate<String, InventoryDTO> kafkaTemplate(ProducerFactory<String, InventoryDTO> pf) {
        KafkaTemplate<String, InventoryDTO> template = new KafkaTemplate<>(pf);
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
    @Bean
    public NewTopic inventoryCreateTopic() {
        return TopicBuilder.name("inventory-create-topic").build();
    }


}
