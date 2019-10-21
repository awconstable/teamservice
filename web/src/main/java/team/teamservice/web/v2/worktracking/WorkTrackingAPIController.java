package team.teamservice.web.v2.worktracking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v2/api/worktracking", produces = "application/json")
public class WorkTrackingAPIController
    {

    @GetMapping("/types")
    public WorkTrackingType[] getEntityTypes()
        {
        return WorkTrackingType.values();
        }

    }
