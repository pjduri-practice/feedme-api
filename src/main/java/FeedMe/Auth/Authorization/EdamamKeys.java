package FeedMe.Auth.Authorization;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "edamam")
@ConfigurationPropertiesScan
public class EdamamKeys {

    private String appId;

    private String appKey;

    public String getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
