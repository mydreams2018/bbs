package cn.kungreat.bbs.social.qq;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QQProperties {
    private String appId;
    private String appSecret;
    private String providerId= "qq";
}
