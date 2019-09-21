package team.teamservice.web.v2;

import org.springframework.data.mongodb.core.index.Indexed;

public class ApplicationId
    {
    @Indexed(unique=true, sparse = true)
    private final String applicationId;
    private final String applicationName;

    public ApplicationId(String applicationId, String applicationName)
        {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        }

    public String getApplicationId()
        {
        return applicationId;
        }

    public String getApplicationName()
        {
        return applicationName;
        }

    @Override
    public String toString()
        {
        return "ApplicationId{" +
                "applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                '}';
        }
    }
