package team.teamservice.web.team;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataMongoTest
public class TeamRepositoryTest
    {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TeamRepository repository;

    @Before
    public void setUp()
        {

        List<Team> teams = new ArrayList<>();
        Team team1 = new Team("team1", "Team 1", null, null, null, null);
        teams.add(team1);
        Team team2 = new Team("team2", "Team 2", team1.getId(), Arrays.asList(team1), null, null);
        teams.add(team2);
        Team team3 = new Team("team3", "Team 3", team2.getId(), Arrays.asList(team1, team2), Arrays.asList("email1@test.test", "email2@test.test"), Arrays.asList("app1", "app2"));
        teams.add(team3);
        Team team4 = new Team("team4", "Team 4", team2.getId(), Arrays.asList(team1, team2), Arrays.asList("email3@test.test", "email4@test.test"), Arrays.asList("app3", "app4"));
        teams.add(team4);
        Team team5 = new Team("team5", "Team 5", team4.getId(), Arrays.asList(team1, team2, team4), Arrays.asList("email5@test.test", "email6@test.test"), Arrays.asList("app5", "app6", "app7", "app8"));
        teams.add(team5);

        repository.saveAll(teams);
        }

    @After
    public void clearDown()
        {
        repository.deleteAll();
        }

    @Test
    public void findBySlugTest() throws Exception
        {

        Optional<Team> team = repository.findBySlug("team1");

        assertThat(team.get().getName()).isEqualTo("Team 1");
        }

    @Test
    public void checkAncestorList()
        {
        Optional<Team> team = repository.findBySlug("team5");

        assertThat(team.get().getAncestors().size()).isEqualTo(3);
        }

    @Test
    public void checkFindByTeamMemberEmail()
        {
        List<Team> teams = repository.findByTeamMemberEmailsIgnoreCase("Email1@Test.Test");

        assertThat(teams.size()).isEqualTo(1);

        assertThat(teams.get(0).getTeamMemberEmails()).contains("email1@test.test");
        }

    @Test
    public void findAllTest() throws Exception
        {

        List<Team> teams = repository.findAll();

        assertThat(teams.size()).isEqualTo(5);
        }

    }
