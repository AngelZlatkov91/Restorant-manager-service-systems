package Report.service.Repost.Service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportDTO {
    private String id;
    private String personalName;
    private Long orderId;
    private LocalDateTime completedIn;
    private Double totalCost;
    private String paymentMethod;
    private LocalDate created;
    private boolean isCheck;
}
