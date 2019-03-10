package team.teamservice.web.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository repository;

    @RequestMapping("/team/ancestors/{id}")
    @ResponseBody
    public List<Team> teamAncestors(Model model, @PathVariable String id) {
         return repository.findAncestors(id);
    }

}
