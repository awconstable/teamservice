package team.teamservice.web.v2.worktracking;

public class WorkTrackingTool
    {

    private WorkTrackingType type;
    private String url;

    public WorkTrackingTool(WorkTrackingType type, String url)
        {
        this.type = type;
        this.url = url;
        }

    public WorkTrackingType getType()
        {
        return type;
        }

    public String getUrl()
        {
        return url;
        }

    @Override
    public String toString()
        {
        return "WorkTrackingTool{" +
                "type=" + type +
                ", url='" + url + '\'' +
                '}';
        }
    }
