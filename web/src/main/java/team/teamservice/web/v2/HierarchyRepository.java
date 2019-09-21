package team.teamservice.web.v2;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "hierarchy", path = "hierarchy")
public interface HierarchyRepository extends MongoRepository<HierarchyEntity, String>, HierarchyRepositoryCustom
    {

    List<HierarchyEntity> findByMembersEmailIgnoreCase(@Param("email") String email);

}
