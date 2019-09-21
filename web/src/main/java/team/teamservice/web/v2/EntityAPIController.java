package team.teamservice.web.v2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v2/api/entity", produces = "application/json")
public class EntityAPIController
    {

    @GetMapping("/types")
    public EntityType[] getEntityTypes()
        {
        return EntityType.values();
        }

    }
