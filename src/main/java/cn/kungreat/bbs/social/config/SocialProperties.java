package cn.kungreat.bbs.social.config;

import cn.kungreat.bbs.social.qq.QQProperties;
import cn.kungreat.bbs.social.weixin.WeiXinProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialProperties {

    private QQProperties qq = new QQProperties();
    private WeiXinProperties weixin = new WeiXinProperties();
}
