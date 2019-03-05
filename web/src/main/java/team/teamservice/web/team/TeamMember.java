package team.teamservice.web.team;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class TeamMember
    {

    @Id
    private String id;
    private String teamId;
    @Indexed(unique = true)
    private String email;

    public TeamMember()
        {
        }

    public TeamMember(String id, String teamId, String email)
        {
        this.id = id;
        this.teamId = teamId;
        this.email = email;
        }

    public String getId()
        {
        return id;
        }

    public String getTeamId()
        {
        return teamId;
        }

    public String getEmail()
        {
        return email;
        }

    @Override
    public String toString()
        {
        return "TeamMember{" +
                "id='" + id + '\'' +
                ", teamId='" + teamId + '\'' +
                ", email='" + email + '\'' +
                '}';
        }
    }
