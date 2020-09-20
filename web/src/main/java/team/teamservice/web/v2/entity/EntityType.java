package team.teamservice.web.v2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EntityType
    {
        COMPANY("Company"),
        DOMAIN("Domain"),
        PLATFORM("Platform"),
        TEAM_OF_TEAMS("Team of Teams"),
        TEAM("Team"),
        APPLICATION("Application"),
        RELEASE("Release");

    private final String key;
    private final String name;

    EntityType(String name)
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