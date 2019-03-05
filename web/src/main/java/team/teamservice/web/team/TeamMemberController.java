package team.teamservice.web.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamMemberController

    {
    @Autowired
    private TeamMemberRepository repository;
    }
