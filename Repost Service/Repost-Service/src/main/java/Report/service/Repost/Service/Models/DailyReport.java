package Report.service.Repost.Service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dailyReport")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyReport {
    @Id
    private String id;
    private String personalName;
    private Double dailyReport;
    private boolean isCheck;
}
