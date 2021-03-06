package team.teamservice.web.v2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import team.teamservice.web.hierarchy.service.HierarchyService;
import team.teamservice.web.v2.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HierarchyController.class)
class HierarchyControllerTest
    {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HierarchyRepository repository;
    @MockBean
    private HierarchyService service;

    @Test
    void ensureCompleteHierarchyBySlugIsSingleJsonObjectNotArray() throws Exception
        {
        List<HierarchyEntity> teamsBelow = new ArrayList<>();

        HierarchyEntity entity = new HierarchyEntity("team1", EntityType.TEAM, "Team 1", null,
            Collections.singletonList(new Relation("team2", EntityType.TEAM, "Team 2", "team1",
                Collections.singletonList(new Relation("team3", EntityType.TEAM, "Team 3", "team2",
                    Collections.emptyList())))), Collections.emptyList(), Collections.emptyList());
        teamsBelow.add(entity);
        when(repository.findHierarchyBelow("team1")).thenReturn(teamsBelow);

        MvcResult result = mockMvc.perform(get("/v2/hierarchy/complete/team1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
 
        String actualResponseBody = result.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace("{\"slug\":\"team1\",\"entityType\":{\"key\":\"TEAM\",\"name\":\"Team\"},\"name\":\"Team 1\"}");
        }

    @Test
    public void checkChildApplicationIds() throws Exception
        {
        when(service.getApplicationHierarchyIds("team1")).thenReturn(Arrays.asList("team1", "team2", "team3"));

        MvcResult result = mockMvc.perform(get("/v2/hierarchy/children/application/ids/team1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace("[\"team1\",\"team2\",\"team3\"]");
        }

    @Test
    public void checkChildIds() throws Exception
        {
        when(service.getChildIds("team1")).thenReturn(Arrays.asList("team1", "team2", "team3"));

        MvcResult result = mockMvc.perform(get("/v2/hierarchy/children/ids/team1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace("[\"team1\",\"team2\",\"team3\"]");
        }
    }