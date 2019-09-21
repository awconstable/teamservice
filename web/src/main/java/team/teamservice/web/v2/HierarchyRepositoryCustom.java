package team.teamservice.web.v2;

import java.util.List;

public interface HierarchyRepositoryCustom
    {
        List<HierarchyEntity> findAncestors(String slug);

        List<HierarchyEntity> findChildren(String slug);
        
        HierarchyEntity findBySlug(String slug);

        List<HierarchyEntity> findCompleteHierarchy();

        List<HierarchyEntity> findHierarchyBelow(String slug);
    }
