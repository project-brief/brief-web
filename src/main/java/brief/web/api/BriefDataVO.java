package brief.web.api;

public class BriefDataVO {

    private static BriefDataVO instance = new BriefDataVO();

    private String url;

    private BriefDataVO() {}

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public static BriefDataVO getInstance() {
        return instance;
    }

}