package team.teamservice.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import team.teamservice.web.team.Team;

@Configuration
public class AppConfig extends RepositoryRestConfigurerAdapter
    {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config)
        {
        config.exposeIdsFor(Team.class);
        }
    }
