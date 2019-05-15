package cn.kungreat.bbs.social.weixin;

public interface WeiXinSubscriber {
    WeiXinSubscriberInfo getSubscriberInfo(String openId);
}
