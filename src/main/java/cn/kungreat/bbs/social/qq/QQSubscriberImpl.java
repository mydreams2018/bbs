package cn.kungreat.bbs.social.qq;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

public class QQSubscriberImpl extends AbstractOAuth2ApiBinding implements QQSubscriber {

    private static final String GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String GET_SUBSCRIBERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openId;

    public QQSubscriberImpl(String accessToken, String appId){
        // token 要求当成参数传送 默认是放在头信息里
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(GET_OPENID,accessToken);
        QQOpenId op = getRestTemplate().getForObject(url, QQOpenId.class);
        this.openId = op.getOpenid();
    }

    @Override
    public QQSubscriberInfo getSubscriberInfo() {
        String url = String.format(GET_SUBSCRIBERINFO,appId,openId);
        QQSubscriberInfo subscriber = getRestTemplate().getForObject(url, QQSubscriberInfo.class);
        subscriber.setOpenId(openId);
        return subscriber;
    }
}
