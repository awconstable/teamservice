package team.teamservice.web.team;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TeamRepositoryTest
    {

    @Autowired
    private TeamRepository repository;

    private final LinkedHashMap<String,Team> teams = new LinkedHashMap<>();

    @BeforeEach
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

    @AfterEach
    public void clearDown()
        {
            repository.deleteAll();
        }

    @Test
    public void findAllTest()
        {

        List<Team> teams = repository.findAll();

        assertThat(teams.size()).isEqualTo(5);
        }

    }
