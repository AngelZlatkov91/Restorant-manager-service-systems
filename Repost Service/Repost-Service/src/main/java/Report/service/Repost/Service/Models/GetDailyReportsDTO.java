package Report.service.Repost.Service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetDailyReportsDTO {
    private String personalName;
    private LocalDate date;
    private Double totalAmounts;
}
