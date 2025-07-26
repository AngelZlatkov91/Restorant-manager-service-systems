package order.services.order.services.Event.Reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyReportsDTO {
    private String personalName;
    private LocalDateTime completedIn;
    private Double totalCost;
    private String paymentMethod;
}
