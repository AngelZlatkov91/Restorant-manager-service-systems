package order.repost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyReportsDTO {
    private Long orderId;
    private String personalName;
    private LocalDateTime completedIn;
    private Double totalCost;
    private String paymentMethod;

}
