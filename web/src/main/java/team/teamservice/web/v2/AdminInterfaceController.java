package team.teamservice.web.v2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminInterfaceController
    {

    @GetMapping("/v2/hierarchy_manager")
    public String hierarchy(Model model)
        {
        return "hierarchy";
        }
    }
