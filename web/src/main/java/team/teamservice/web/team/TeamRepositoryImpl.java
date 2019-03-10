package team.teamservice.web.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

public class TeamRepositoryImpl implements TeamRepositoryCustom
    {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TeamRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Team> findAncestors(String slug)
        {

        TypedAggregation<Team> agg = Aggregation.newAggregation(Team.class,
                match(Criteria.where("slug").is(slug)),
                Aggregation.graphLookup("team")
                        .startWith("parentSlug")
                        .connectFrom("parentSlug")
                        .connectTo("slug")
                        .as("ancestors"));

        AggregationResults<Team> result = mongoTemplate.aggregate(agg, Team.class);

        System.out.println(result.getRawResults());


        List<Team> teams = result.getMappedResults();

        return teams;
        }

    @Override
    public List<Team> findChildren(String id)
        {
        return null;
        }
    }
