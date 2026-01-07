package Report.service.Repost.Service.Repositories;

import Report.service.Repost.Service.Models.DailyReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DailyReportRepositories extends MongoRepository<DailyReport, String> {
    Optional<DailyReport> findByPersonalName(String personalName);
}
