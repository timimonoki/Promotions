package promotions.utils.conf;

public class SiteConfigurations {

    //browser related configuration
    private String browserType;

    //site URLs related configuration
    private String lidlUrl;
    private String kauflandUrl;

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getLidlUrl() {
        return lidlUrl;
    }

    public void setLidlUrl(String lidlUrl) {
        this.lidlUrl = lidlUrl;
    }

    public String getKauflandUrl() {
        return kauflandUrl;
    }

    public void setKauflandUrl(String kauflandUrl) {
        this.kauflandUrl = kauflandUrl;
    }
}
