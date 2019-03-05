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
    private final String parentId;
    private final Collection<Team> ancestors;

    public Team(String slug, String name, String parentId, Collection<Team> ancestors)
        {
        this.slug = slug;
        this.name = name;
        this.parentId = parentId;
        this.ancestors = ancestors;
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

    public Collection<Team> getAncestors() { return ancestors; }

    @Override
    public String toString()
        {
        return "Team{" +
                "id='" + id + '\'' +
                ", slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                ", parentId='" + parentId + '\'' +
                ", ancestors=" + ancestors +
                '}';
        }
    }
