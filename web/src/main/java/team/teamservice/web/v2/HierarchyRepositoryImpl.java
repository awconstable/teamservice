package team.teamservice.web.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

public class HierarchyRepositoryImpl implements HierarchyRepositoryCustom
    {
    
    private final MongoTemplate mongoTemplate;

    @Autowired
    public HierarchyRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<HierarchyEntity> findAncestors(String slug)
        {

        TypedAggregation<HierarchyEntity> agg = Aggregation.newAggregation(HierarchyEntity.class,
                match(Criteria.where("slug").is(slug)),
                Aggregation.graphLookup("hierarchyEntity")
                        .startWith("parentSlug")
                        .connectFrom("parentSlug")
                        .connectTo("slug")
                        .as("ancestors"));

        AggregationResults<HierarchyEntity> result = mongoTemplate.aggregate(agg, HierarchyEntity.class);

        return result.getMappedResults();
        }

    @Override
    public List<HierarchyEntity> findChildren(String slug)
        {

        TypedAggregation<HierarchyEntity> agg = Aggregation.newAggregation(HierarchyEntity.class,
                match(Criteria.where("slug").is(slug)),
                Aggregation.graphLookup("hierarchyEntity")
                        .startWith("slug")
                        .connectFrom("slug")
                        .connectTo("parentSlug")
                        .as("children"));

        AggregationResults<HierarchyEntity> result = mongoTemplate.aggregate(agg, HierarchyEntity.class);

        return result.getMappedResults();
        }

    @Override
    public HierarchyEntity findBySlug(String slug)
        {
        TypedAggregation<HierarchyEntity> agg = Aggregation.newAggregation(HierarchyEntity.class,
                match(Criteria.where("slug").is(slug)),
                Aggregation.graphLookup("hierarchyEntity")
                        .startWith("slug")
                        .connectFrom("slug")
                        .connectTo("parentSlug")
                        .as("children"),
                Aggregation.graphLookup("hierarchyEntity")
                        .startWith("parentSlug")
                        .connectFrom("parentSlug")
                        .connectTo("slug")
                        .as("ancestors"));

        AggregationResults<HierarchyEntity> result = mongoTemplate.aggregate(agg, HierarchyEntity.class);

        return result.getUniqueMappedResult();
        }


    @Override
    public List<HierarchyEntity> findCompleteHierarchy(){
        return findHierarchy("parentSlug", "");
    }

    @Override
    public List<HierarchyEntity> findHierarchyBelow(String slug) {
        return findHierarchy("slug", slug);
    }
    
    private List<HierarchyEntity> findHierarchy(String filter, String slug)
        {
        TypedAggregation<HierarchyEntity> agg = Aggregation.newAggregation(HierarchyEntity.class,
                match(Criteria.where(filter).is(slug)),
                Aggregation.graphLookup("hierarchyEntity")
                        .startWith("slug")
                        .connectFrom("slug")
                        .connectTo("parentSlug")
                        .as("children"));

        AggregationResults<HierarchyEntity> result = mongoTemplate.aggregate(agg, HierarchyEntity.class);

        return result.getMappedResults();
        }


    }
