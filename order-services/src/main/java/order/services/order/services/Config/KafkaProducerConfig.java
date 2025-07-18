package order.services.order.services.Config;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic newTopic() {

        return TopicBuilder.name("kitchen-display-event.v1").build();
    }
}
