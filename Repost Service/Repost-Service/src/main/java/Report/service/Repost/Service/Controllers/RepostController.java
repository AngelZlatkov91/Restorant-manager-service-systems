package Report.service.Repost.Service.Controllers;

import Report.service.Repost.Service.Models.PersonalCostSummary;
import Report.service.Repost.Service.Services.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/getRepost")
public class RepostController {

    private final ReportService reportService;

    public RepostController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<List<PersonalCostSummary>> getRepost() {
        return ResponseEntity.ok(reportService.getPersonalReport());
    }
}
