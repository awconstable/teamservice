package team.teamservice.web.team;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class TeamRepositoryTest
    {

    @Autowired
    private TeamRepository repository;

    private LinkedHashMap<String,Team> teams = new LinkedHashMap<>();

    @Before
    public void setUp()
        {

        Team team1 = new Team("team1", "Team 1", "", null, null, null, null);
        teams.put("team1", team1);
        Team team2 = new Team("team2", "Team 2", team1.getSlug(), null, null, null, null);
        teams.put("team2", team2);
        Team team3 = new Team("team3", "Team 3", team2.getSlug(), null, null, Arrays.asList(new TeamMember("email1@test.test",null), new TeamMember("email2@test.test", null)), Arrays.asList(new Application("app1", null), new Application("app2", null)));
        teams.put("team3", team3);
        Team team4 = new Team("team4", "Team 4", team3.getSlug(), null, null, Arrays.asList(new TeamMember("email3@test.test",null), new TeamMember("email4@test.test", null)), Arrays.asList(new Application("app3", null), new Application("app4", null)));
        teams.put("team4", team4);
        Team team5 = new Team("team5", "Team 5", team4.getSlug(), null, null, Arrays.asList(new TeamMember("email5@test.test",null), new TeamMember("email6@test.test", null)), Arrays.asList(new Application("app5", null), new Application("app6", null),new Application("app7", null), new Application("app8", null)));
        teams.put("team5", team5);

        repository.saveAll(teams.values());
        }

    @After
    public void clearDown()
        {
            repository.deleteAll();
        }

    @Test
    public void findBySlugTest()
        {

        Team team = repository.findBySlug("team1");

        assertThat(team.getName()).isEqualTo("Team 1");
        }

    @Test
    public void checkFindByTeamMemberEmail()
        {
        List<Team> teams = repository.findByTeamMembersEmailIgnoreCase("Email1@Test.Test");

        assertThat(teams.size()).isEqualTo(1);

        Collection<TeamMember> members = teams.get(0).getTeamMembers();
        boolean emailMatch = false;
        for(TeamMember member:members){
            if("email1@test.test".equals(member.getEmail())){
                emailMatch = true;
            }
        }
        
        assertThat(emailMatch).isEqualTo(true);
        }

    @Test
    public void findAllTest()
        {

        List<Team> teams = repository.findAll();

        assertThat(teams.size()).isEqualTo(5);
        }

    @Test
    public void findAncestors()
        {

        List<Team> ancestorTeams = repository.findAncestors(teams.get("team5").getSlug());
        
        assertThat(ancestorTeams.size()).isEqualTo(1);

        assertThat(ancestorTeams.get(0).getAncestors().size()).isEqualTo(4);
        }

    @Test
    public void findChildren()
        {

        List<Team> childTeams = repository.findChildren(teams.get("team1").getSlug());

        assertThat(childTeams.size()).isEqualTo(1);

        assertThat(childTeams.get(0).getChildren().size()).isEqualTo(4);
        }

    @Test
    public void findCompleteHierarchy()
        {

        List<Team> childTeams = repository.findCompleteHierarchy();

        assertThat(childTeams.size()).isEqualTo(1);

        assertThat(childTeams.get(0).getSlug()).isEqualTo("team1");

        assertThat(childTeams.get(0).getChildren().size()).isEqualTo(4);
        }

    @Test
    public void findFilteredHierarchy()
        {

        List<Team> childTeams = repository.findHierarchyBelow(teams.get("team2").getSlug());
        
        assertThat(childTeams.size()).isEqualTo(1);

        assertThat(childTeams.get(0).getSlug()).isEqualTo("team2");

        assertThat(childTeams.get(0).getChildren().size()).isEqualTo(3);
        }

    }
