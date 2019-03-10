package team.teamservice.web.team;

import java.util.List;

public interface TeamRepositoryCustom
    {
        List<Team> findAncestors(String id);

        List<Team> findChildren(String id);
    }
