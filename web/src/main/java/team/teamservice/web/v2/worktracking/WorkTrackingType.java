package team.teamservice.web.v2.worktracking;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkTrackingType
    {
        JIRA("Jira"),
        LEANKIT("LeanKit");

    private final String key;
    private final String name;

    WorkTrackingType(String name)
        {
        this.key = this.name();
        this.name = name;
        }

    public String getKey()
        {
        return key;
        }

    public String getName()
        {
        return name;
        }

    @Override
    public String toString()
        {
        return "EntityType{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
        }
    }
