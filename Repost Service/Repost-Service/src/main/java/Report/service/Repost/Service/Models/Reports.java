package Report.service.Repost.Service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collation = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reports {
    @Id
    private String id;
    private String personalName;
    private Long orderId;
    private LocalDateTime completedIn;
    private Double totalCost;
    private String paymentMethod;
    private LocalDate created;
}
