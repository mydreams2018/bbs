package cn.kungreat.bbs.social.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter  filter  = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl("/auth");
        return (T)filter;
    }
}
