package team.teamservice.web.migrate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team.teamservice.web.team.*;
import team.teamservice.web.v2.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/v2/migration", produces = "application/json")
public class MigrationController
    {
    
    private final TeamRepository teamRepository;
    private final HierarchyRepository hierarchyRepository;

    @Autowired
    public MigrationController(TeamRepository teamRepository, HierarchyRepository hierarchyRepository)
        {
        this.teamRepository = teamRepository;
        this.hierarchyRepository = hierarchyRepository;
        }

        @GetMapping("/teams_to_entities")
        @ResponseStatus(HttpStatus.OK)
        public String migrateTeamsToEntities(){

            List<Team> teams = teamRepository.findAll();

            int entityMigrationCount = 0;

            for(Team team:teams){
                HierarchyEntity existingTeam = hierarchyRepository.findBySlug(team.getSlug());
                if(existingTeam == null){
                    HierarchyEntity newEntity = new HierarchyEntity(team.getSlug(), EntityType.TEAM,
                            team.getName(), team.getParentSlug(), convertRelations(team.getAncestors()),
                            convertRelations(team.getChildren()), convertMembers(team.getTeamMembers()),
                            convertApplicationIds(team.getApplications()));
                    hierarchyRepository.save(newEntity);
                    entityMigrationCount++;
                }
            }

            return "{ entityMigrationCount : " + entityMigrationCount + " }";
        }

    private Collection<Relation> convertRelations(Collection<TeamRelation> teamRelations){
        if(teamRelations == null){
            return null;
        }

        Collection<Relation> relations = new ArrayList<>();
        for(TeamRelation teamRelation:teamRelations){
           Relation relation = new Relation(teamRelation.getSlug(), EntityType.TEAM, teamRelation.getName(),
                   teamRelation.getParentSlug(), convertRelations(teamRelation.getChildren()));
           relations.add(relation);
        }
        return relations;
    }

    private Collection<Member> convertMembers(Collection<TeamMember> teamMembers){
        if(teamMembers == null){
            return null;
        }
        Collection<Member> members = new ArrayList<>();
        for(TeamMember teamMember:teamMembers){
            Member member = new Member(teamMember.getEmail(), teamMember.getName());
            members.add(member);
        }
        return members;
    }

    private Collection<ApplicationId> convertApplicationIds(Collection<Application> applications){
        if(applications == null){
            return null;
        }
        Collection<ApplicationId> applicationIds = new ArrayList<>();
        for(Application application:applications){
            ApplicationId applicationId = new ApplicationId(application.getApplicationId(), application.getApplicationName());
            applicationIds.add(applicationId);
        }
        return applicationIds;
    }
    
    }
