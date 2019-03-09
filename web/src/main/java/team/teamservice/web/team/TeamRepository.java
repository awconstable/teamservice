package team.teamservice.web.team;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "team", path = "team")
public interface TeamRepository extends MongoRepository<Team, String>  {

    Optional<Team> findBySlug(@Param("slug") String slug);

    List<Team> findByTeamMemberEmailsIgnoreCase(@Param("email") String email);
}
