package Report.service.Repost.Service.Controllers;

import Report.service.Repost.Service.Models.DailyReportDTO;

import Report.service.Repost.Service.Models.ResPersonalName;
import Report.service.Repost.Service.Services.DailyReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/getRepost")
public class RepostController {

    private final DailyReportsService dailyReportsService;

    public RepostController( DailyReportsService dailyReportsService) {

        this.dailyReportsService = dailyReportsService;
    }

    @PostMapping
    public ResponseEntity<DailyReportDTO> getRepost(@RequestBody ResPersonalName resPersonalName) {
        return ResponseEntity.ok(dailyReportsService.getReport(resPersonalName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> isCheck(@PathVariable String id) {
        dailyReportsService.isChek(id);
        return ResponseEntity.ok("Is Check");
    }

}
