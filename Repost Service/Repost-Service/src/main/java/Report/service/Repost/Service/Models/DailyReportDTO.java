package Report.service.Repost.Service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyReportDTO {
    private String id;
    private String personalName;
    private Double dailyReport;

}
