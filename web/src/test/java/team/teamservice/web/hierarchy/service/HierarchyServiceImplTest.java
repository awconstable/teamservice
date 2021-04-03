package team.teamservice.web.hierarchy.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import team.teamservice.web.v2.HierarchyEntity;
import team.teamservice.web.v2.HierarchyRepository;
import team.teamservice.web.v2.Relation;
import team.teamservice.web.v2.entity.EntityType;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class HierarchyServiceImplTest
    {
    @Autowired
    private HierarchyRepository mockHierarchyRepo;
    @Autowired
    private HierarchyService hierarchyService;
    @TestConfiguration
    static class HierarchyServiceImplTestContextConfiguration
        {
        @MockBean
        private HierarchyRepository mockHierarchyRepo;
        @Bean
        public HierarchyService hierarchyService()
            {
            return new HierarchyServiceImpl(mockHierarchyRepo);
            }
        }

    @Test
    void getHierarchyIds()
        {
        String appId = "a1";
        Collection<Relation> children = Collections.singletonList(new Relation("a2", EntityType.APPLICATION, "App 2", appId, Collections.emptyList()));
        HierarchyEntity entity = new HierarchyEntity(appId, EntityType.APPLICATION, "App 1", "", Collections.emptyList(), children, Collections.emptyList());
        when(mockHierarchyRepo.findBySlug(appId)).thenReturn(entity);

        Collection<String> teamIds = hierarchyService.getApplicationHierarchyIds(appId);

        assertThat(teamIds, hasItems("a1", "a2"));
        verify(mockHierarchyRepo, times(1)).findBySlug(appId);
        }

    @Test
    void getHierarchyIdsNoChildren()
        {
        String appId = "a1";
        HierarchyEntity entity = new HierarchyEntity(appId, EntityType.APPLICATION, "App 1", "", Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        when(mockHierarchyRepo.findBySlug(appId)).thenReturn(entity);

        Collection<String> teamIds = hierarchyService.getApplicationHierarchyIds(appId);

        assertThat(teamIds, hasItems("a1"));
        verify(mockHierarchyRepo, times(1)).findBySlug(appId);
        }

    @Test
    void getHierarchyIdsCantFindEntity()
        {
        String appId = "a1";
        when(mockHierarchyRepo.findBySlug(appId)).thenReturn(null);

        Collection<String> teamIds = hierarchyService.getApplicationHierarchyIds(appId);

        assertThat(teamIds, hasItems("a1"));
        verify(mockHierarchyRepo, times(1)).findBySlug(appId);
        }

    @Test
    void getHierarchyIdsTeamOfTeams()
        {
        String appId = "a1";
        Collection<Relation> children = Collections.singletonList(new Relation("a2", EntityType.APPLICATION, "App 2", appId, Collections.emptyList()));
        HierarchyEntity entity = new HierarchyEntity(appId, EntityType.TEAM_OF_TEAMS, "App 1", "", Collections.emptyList(), children, Collections.emptyList());
        when(mockHierarchyRepo.findBySlug(appId)).thenReturn(entity);

        Collection<String> teamIds = hierarchyService.getApplicationHierarchyIds(appId);

        assertThat(teamIds, hasItem("a1"));
        assertThat(teamIds.size(), is(equalTo(1)));
        verify(mockHierarchyRepo, times(1)).findBySlug(appId);
        }

    @Test
    void getChildIds()
        {
        String appId = "a1";
        Collection<Relation> children = Collections.singletonList(new Relation("a2", EntityType.APPLICATION, "App 2", appId, Collections.emptyList()));
        HierarchyEntity entity = new HierarchyEntity(appId, EntityType.APPLICATION, "App 1", "", Collections.emptyList(), children, Collections.emptyList());
        when(mockHierarchyRepo.findBySlug(appId)).thenReturn(entity);

        Collection<String> teamIds = hierarchyService.getChildIds(appId);

        assertThat(teamIds, hasItems("a1", "a2"));
        verify(mockHierarchyRepo, times(1)).findBySlug(appId);
        }
    }