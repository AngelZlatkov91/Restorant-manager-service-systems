package Report.service.Repost.Service.Repositories;

import Report.service.Repost.Service.Models.PersonalCostSummary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final MongoTemplate mongoTemplate;

    public OrderCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<PersonalCostSummary> getTotalCostByPersonalNameAndDate(String paymentMethod, LocalDate startDate, LocalDate endDate) {
        MatchOperation match = Aggregation.match(
                Criteria.where("paymentMethod").is(paymentMethod)
                        .and("created").gte(startDate).lte(endDate)
        );

        GroupOperation group = Aggregation.group("personalName").sum("totalCost").as("totalCostSum");

        ProjectionOperation project = Aggregation.project()
                .and("_id").as("personalName")
                .and("totalCostSum").as("totalCostSum");

        Aggregation aggregation = Aggregation.newAggregation(match,group,project);

        return mongoTemplate.aggregate(aggregation, "reports", PersonalCostSummary.class).getMappedResults();
    }
}
