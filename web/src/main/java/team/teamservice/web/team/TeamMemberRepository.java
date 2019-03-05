package team.teamservice.web.team;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "teammember", path = "teammember")
public interface TeamMemberRepository extends MongoRepository<TeamMember, String>
    {
        List<TeamMember> findByTeamId(@Param("teamId") String teamId);

        List<TeamMember> findByEmailIgnoreCase(@Param("email") String email);
    }
