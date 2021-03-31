package team.teamservice.web.hierarchy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.teamservice.web.v2.HierarchyEntity;
import team.teamservice.web.v2.HierarchyRepository;
import team.teamservice.web.v2.entity.EntityType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class HierarchyServiceImpl implements HierarchyService
    {
    
    private final HierarchyRepository hierarchyRepository;

    @Autowired
    public HierarchyServiceImpl(HierarchyRepository hierarchyRepository)
        {
        this.hierarchyRepository = hierarchyRepository;
        }

    @Override
    public Collection<String> getApplicationHierarchyIds(String applicationId)
        {
        Set<String> teams = new HashSet<>();
        teams.add(applicationId);
        HierarchyEntity team = hierarchyRepository.findBySlug(applicationId);
        if(team == null){
            return teams;
        }
        if(EntityType.APPLICATION.equals(team.getEntityType())){
            team.getChildren().forEach(child -> teams.add(child.getSlug()));
        }
        return teams;
        }
    }
