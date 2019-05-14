package team.teamservice.web.team;

import java.util.List;

public interface TeamRepositoryCustom
    {
        List<Team> findAncestors(String slug);

        List<Team> findChildren(String slug);
        
        Team findBySlug(String slug);

        List<Team> findCompleteHierarchy();

        List<Team> findHierarchyBelow(String slug);
    }
