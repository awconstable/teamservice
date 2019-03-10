package team.teamservice.web.team;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Collection;

public class Team
    {

    @Id
    private String id;
    @Indexed(unique=true)
    private final String slug;
    private final String name;
    private final String parentSlug;
    private final Collection<Team> ancestors;
    private final Collection<String> teamMemberEmails;
    private final Collection<String> applicationIds;

    public Team(String slug, String name, String parentSlug, Collection<Team> ancestors, Collection<String> teamMemberEmails, Collection<String> applicationIds)
        {
        this.slug = slug;
        this.name = name;
        this.parentSlug = parentSlug;
        this.ancestors = ancestors;
        this.teamMemberEmails = teamMemberEmails;
        this.applicationIds = applicationIds;
        }
    
    public String getId() { return id; }

    public String getSlug()
        {
        return slug;
        }

    public String getName()
        {
        return name;
        }

    public String getParentSlug() { return parentSlug; }

    public Collection<Team> getAncestors() { return ancestors; }

    public Collection<String> getTeamMemberEmails() { return teamMemberEmails; }

    public Collection<String> getApplicationIds() { return applicationIds; }

    @Override
    public String toString()
        {
        return "Team{" +
                "id='" + id + '\'' +
                ", slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                ", parentSlug='" + parentSlug + '\'' +
                ", ancestors=" + ancestors +
                ", teamMemberEmails=" + teamMemberEmails +
                ", applicationIds=" + applicationIds +
                '}';
        }
    }
