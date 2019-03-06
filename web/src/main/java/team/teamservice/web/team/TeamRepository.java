package team.teamservice.web.team;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "team", path = "team")
public interface TeamRepository extends MongoRepository<Team, String>  {

    Optional<Team> findBySlug(String slug);

}