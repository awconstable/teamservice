package team.teamservice.web.v2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import team.teamservice.web.v2.entity.EntityType;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class HierarchyRepositoryTest
    {

    @Autowired
    private HierarchyRepository repository;

    private final LinkedHashMap<String, HierarchyEntity> entities = new LinkedHashMap<>();

    @BeforeEach
    public void setUp()
        {

        HierarchyEntity team1 = new HierarchyEntity("team1", EntityType.TEAM, "Team 1", "", null, null, null, null, null);
        entities.put("team1", team1);
        HierarchyEntity team2 = new HierarchyEntity("team2", EntityType.TEAM,"Team 2", team1.getSlug(), null, null, null,null, null);
        entities.put("team2", team2);
        HierarchyEntity team3 = new HierarchyEntity("team3", EntityType.TEAM,"Team 3", team2.getSlug(), null, null,null, Arrays.asList(new Member("email1@test.test",null), new Member("email2@test.test", null)), Arrays.asList(new ApplicationId("app1", null), new ApplicationId("app2", null)));
        entities.put("team3", team3);
        HierarchyEntity team4 = new HierarchyEntity("team4", EntityType.TEAM,"Team 4", team3.getSlug(), null, null, null, Arrays.asList(new Member("email3@test.test",null), new Member("email4@test.test", null)), Arrays.asList(new ApplicationId("app3", null), new ApplicationId("app4", null)));
        entities.put("team4", team4);
        HierarchyEntity team5 = new HierarchyEntity("team5", EntityType.APPLICATION,"Team 5", team4.getSlug(), null, null, null, Arrays.asList(new Member("email5@test.test",null), new Member("email6@test.test", null)), Arrays.asList(new ApplicationId("app5", null), new ApplicationId("app6", null),new ApplicationId("app7", null), new ApplicationId("app8", null)));
        entities.put("team5", team5);

        repository.saveAll(entities.values());
        }

    @AfterEach
    public void clearDown()
        {
        repository.deleteAll();
        }

    @Test
    public void findBySlugTest()
        {

        HierarchyEntity team = repository.findBySlug("team1");

        assertThat(team.getName()).isEqualTo("Team 1");
        }

    @Test
    public void checkFindByTeamMemberEmail()
        {
        List<HierarchyEntity> teams = repository.findByMembersEmailIgnoreCase("Email1@Test.Test");

        assertThat(teams.size()).isEqualTo(1);

        Collection<Member> members = teams.get(0).getMembers();
        boolean emailMatch = false;
        for(Member member:members){
        if ("email1@test.test".equals(member.getEmail()))
            {
            emailMatch = true;
            break;
            }
        }

        assertThat(emailMatch).isEqualTo(true);
        }

    @Test
    public void findAllTest()
        {

        List<HierarchyEntity> teams = repository.findAll();

        assertThat(teams.size()).isEqualTo(5);
        }

    @Test
    public void findAncestors()
        {

        List<HierarchyEntity> ancestorTeams = repository.findAncestors(entities.get("team5").getSlug());

        assertThat(ancestorTeams.size()).isEqualTo(1);

        assertThat(ancestorTeams.get(0).getAncestors().size()).isEqualTo(4);
        }

    @Test
    public void findChildren()
        {

        List<HierarchyEntity> childTeams = repository.findChildren(entities.get("team1").getSlug());

        assertThat(childTeams.size()).isEqualTo(1);

        assertThat(childTeams.get(0).getChildren().size()).isEqualTo(4);
        }

    @Test
    public void findCompleteHierarchy()
        {

        List<HierarchyEntity> childTeams = repository.findCompleteHierarchy();

        assertThat(childTeams.size()).isEqualTo(1);

        assertThat(childTeams.get(0).getSlug()).isEqualTo("team1");

        assertThat(childTeams.get(0).getChildren().size()).isEqualTo(4);
        }

    @Test
    public void findFilteredHierarchy()
        {

        List<HierarchyEntity> childTeams = repository.findHierarchyBelow(entities.get("team2").getSlug());

        assertThat(childTeams.size()).isEqualTo(1);

        assertThat(childTeams.get(0).getSlug()).isEqualTo("team2");

        assertThat(childTeams.get(0).getChildren().size()).isEqualTo(3);
        }

    }