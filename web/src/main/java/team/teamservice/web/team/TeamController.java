package team.teamservice.web.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class TeamController
    {

    private final TeamRepository repository;

    @Autowired
    public TeamController(TeamRepository repository)
        {
        this.repository = repository;
        }

    @RequestMapping("/teams/ancestors/{slug}")
    @ResponseBody
    public List<Team> teamAncestors(@PathVariable String slug)
        {
        return repository.findAncestors(slug);
        }

    @RequestMapping("/teams/children/{slug}")
    @ResponseBody
    public List<Team> teamChildren(@PathVariable String slug)
        {
        return repository.findChildren(slug);
        }

    @RequestMapping("/teams/relatives/{slug}")
    @ResponseBody
    public Team teamIncludingRelatives(@PathVariable String slug)
        {
        return repository.findBySlug(slug);
        }

    @RequestMapping("/teams/hierarchy/{slug}")
    @ResponseBody
    public List<TeamRelation> teamHierarchy(@PathVariable String slug)
        {

        List<TeamRelation> teamHierarchy = new ArrayList<>();

        List<Team> teams;

        if("all".equals(slug)){
            teams =  repository.findCompleteHierarchy();
        } else {
            teams = repository.findHierarchyBelow(slug);
        }

        for(Team team: teams){
            TeamRelation rootTemp = team.getTeamRelation();

            List<TeamRelation> children = findChildren(team.getSlug(), team.getChildren());

            TeamRelation root = new TeamRelation(rootTemp.getSlug(), rootTemp.getName(), rootTemp.getParentSlug(), children);

            teamHierarchy.add(root);
        }

         return teamHierarchy;
        }

    @RequestMapping("/teams/all")
    @ResponseBody
    public List<Team> allTeams()
        {
        return repository.findAll();
        }

    private List<TeamRelation> findChildren(String parentSlug, Collection<TeamRelation> children){
        List<TeamRelation> directChildren = new ArrayList<>();
        for(TeamRelation child: children){
            if(parentSlug.equals(child.getParentSlug())){
               List<TeamRelation> grandChildren = findChildren(child.getSlug(), children);
               TeamRelation grandChild = new TeamRelation(child.getSlug(), child.getName(), child.getParentSlug(), grandChildren);
               directChildren.add(grandChild);
            }
        }
        return directChildren;
    }

    }
