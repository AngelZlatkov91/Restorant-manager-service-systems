package Report.service.Repost.Service.Event.SendToPrintReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportForDay {
    private String personalName;
    private LocalDateTime createdIn;
    private Double totalAmount;
}
