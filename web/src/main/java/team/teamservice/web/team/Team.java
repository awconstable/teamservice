package team.teamservice.web.team;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
public class Team
    {

    @Id
    private String id;
    @Indexed(unique=true)
    private final String slug;
    private final String name;
    private final String parentSlug;
    private final Collection<TeamRelation> ancestors;
    private final Collection<TeamRelation> children;
    private final Collection<TeamMember> teamMembers;
    private final Collection<Application> applications;

    public Team(String slug, String name, String parentSlug, Collection<TeamRelation> ancestors, Collection<TeamRelation> children, Collection<TeamMember> teamMembers, Collection<Application> applications)
        {
        this.slug = slug;
        this.name = name;
        this.parentSlug = parentSlug;
        this.ancestors = ancestors;
        this.children = children;
        this.teamMembers = teamMembers;
        this.applications = applications;
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

    public Collection<TeamRelation> getAncestors() { return ancestors; }

    public Collection<TeamRelation> getChildren() { return children; }

    public Collection<TeamMember> getTeamMembers() { return teamMembers; }

    public Collection<Application> getApplications() { return applications; }

    public TeamRelation getTeamRelation() {
        return new TeamRelation(this.slug, this.name, this.parentSlug, null);
    }

    @Override
    public String toString()
        {
        return "Team{" +
                "id='" + id + '\'' +
                ", slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                ", parentSlug='" + parentSlug + '\'' +
                ", ancestors=" + ancestors +
                ", children=" + children +
                ", teamMembers=" + teamMembers +
                ", applications=" + applications +
                '}';
        }
    }
