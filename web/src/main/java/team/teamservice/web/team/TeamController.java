package team.teamservice.web.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

    @Autowired
    private TeamRepository repository;

    @RequestMapping("/teams/ancestors/{slug}")
    @ResponseBody
    public List<Team> teamAncestors(Model model, @PathVariable String slug)
        {
        return repository.findAncestors(slug);
        }

    @RequestMapping("/teams/children/{slug}")
    @ResponseBody
    public List<Team> teamChildren(Model model, @PathVariable String slug)
        {
        return repository.findChildren(slug);
        }

    @RequestMapping("/teams/relatives/{slug}")
    @ResponseBody
    public Team teamIncludingRelatives(Model model, @PathVariable String slug)
        {
        return repository.findBySlug(slug);
        }

    @RequestMapping("/teams/hierarchy/all")
    @ResponseBody
    public List<TeamRelation> teamHierarchy(Model model)
        {

        List<TeamRelation> teamHierarchy = new ArrayList<>();

        List<Team> teams = repository.findHierarchy();

        for(Team team: teams){
            TeamRelation rootTemp = team.getTeamRelation();

            List<TeamRelation> children = findChildren(team.getSlug(), team.getChildren());

            TeamRelation root = new TeamRelation(rootTemp.getSlug(), rootTemp.getName(), rootTemp.getParentSlug(), children);

            teamHierarchy.add(root);
        }

         return teamHierarchy;
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
