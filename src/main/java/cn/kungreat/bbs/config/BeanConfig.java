package cn.kungreat.bbs.config;

import cn.kungreat.bbs.security.ImageFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Properties;

@Configuration
public class BeanConfig {

    @Bean(initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "datasource1")
    public DruidDataSource initDruid(){
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ImageFilter());
        registration.addUrlPatterns("/register");
        registration.setName("imgFilter");
        return registration;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "360");
        properties.setProperty("kaptcha.image.height", "70");
        properties.setProperty("kaptcha.textproducer.font.size", "60");
        properties.setProperty("kaptcha.textproducer.char.length", "6");
        properties.setProperty("kaptcha.textproducer.char.space", "6");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DruidDataSource dataSource){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //启动创建 一个数据表用来存放token   只能用一次
//		tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}