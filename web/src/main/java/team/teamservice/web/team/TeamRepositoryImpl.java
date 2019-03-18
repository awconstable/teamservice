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

        List<Team> teams = result.getMappedResults();

        return teams;
        }

    @Override
    public List<Team> findChildren(String slug)
        {

        TypedAggregation<Team> agg = Aggregation.newAggregation(Team.class,
                match(Criteria.where("slug").is(slug)),
                Aggregation.graphLookup("team")
                        .startWith("slug")
                        .connectFrom("slug")
                        .connectTo("parentSlug")
                        .as("children"));

        AggregationResults<Team> result = mongoTemplate.aggregate(agg, Team.class);

        List<Team> teams = result.getMappedResults();

        return teams;
        }

    @Override
    public Team findBySlug(String slug)
        {
        TypedAggregation<Team> agg = Aggregation.newAggregation(Team.class,
                match(Criteria.where("slug").is(slug)),
                Aggregation.graphLookup("team")
                        .startWith("slug")
                        .connectFrom("slug")
                        .connectTo("parentSlug")
                        .as("children"),
                Aggregation.graphLookup("team")
                        .startWith("parentSlug")
                        .connectFrom("parentSlug")
                        .connectTo("slug")
                        .as("ancestors"));

        AggregationResults<Team> result = mongoTemplate.aggregate(agg, Team.class);

        Team team = result.getUniqueMappedResult();

        System.out.println(result.getRawResults());

        return team;
        }

    @Override
    public List<Team> findHierarchy()
        {
        TypedAggregation<Team> agg = Aggregation.newAggregation(Team.class,
                match(Criteria.where("parentSlug").is("")),
                Aggregation.graphLookup("team")
                        .startWith("slug")
                        .connectFrom("slug")
                        .connectTo("parentSlug")
                        .as("children"));

        AggregationResults<Team> result = mongoTemplate.aggregate(agg, Team.class);

        List<Team> teams = result.getMappedResults();

        System.out.println(result.getRawResults());

        return teams;
        }


    }
