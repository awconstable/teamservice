package team.teamservice.web.team;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepositoryCustom
    {
        List<Team> findAncestors(String slug);

        List<Team> findChildren(String slug);
        
        Team findBySlug(String slug);
    }
