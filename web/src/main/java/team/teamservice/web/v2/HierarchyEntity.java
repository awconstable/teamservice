package team.teamservice.web.v2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import team.teamservice.web.v2.entity.EntityType;

import java.util.Collection;

@Document
public class HierarchyEntity
    {

    @Id
    private String id;
    @Indexed(unique=true)
    private final String slug;
    private final EntityType entityType;
    private final String name;
    private final String parentSlug;
    private final Collection<Relation> ancestors;
    private final Collection<Relation> children;
    private final Collection<Member> members;

    public HierarchyEntity(String slug, EntityType entityType, String name, String parentSlug, Collection<Relation> ancestors, Collection<Relation> children, Collection<Member> members)
        {
        this.slug = slug;
        this.entityType = entityType;
        this.name = name;
        this.parentSlug = parentSlug;
        this.ancestors = ancestors;
        this.children = children;
        this.members = members;
        }

    public String getId() { return id; }

    public String getSlug()
        {
        return slug;
        }

    public EntityType getEntityType() { return entityType; }

    public String getName()
        {
        return name;
        }

    public String getParentSlug() { return parentSlug; }

    public Collection<Relation> getAncestors() { return ancestors; }

    public Collection<Relation> getChildren() { return children; }

    public Collection<Member> getMembers() { return members; }

    @JsonIgnore
    public Relation getRelation() {
        return new Relation(this.slug, entityType, this.name, this.parentSlug, null);
    }

    @Override
    public String toString()
        {
        return "HierarchyEntity{" +
            "id='" + id + '\'' +
            ", slug='" + slug + '\'' +
            ", entityType=" + entityType +
            ", name='" + name + '\'' +
            ", parentSlug='" + parentSlug + '\'' +
            ", ancestors=" + ancestors +
            ", children=" + children +
            ", members=" + members +
            '}';
        }
    }
