package Report.service.Repost.Service.Repositories;

import Report.service.Repost.Service.Models.Reports;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepositories extends MongoRepository<Reports, String> {


}
