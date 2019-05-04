package cn.kungreat.bbs.config;

import cn.kungreat.bbs.security.FaliureHandler;
import cn.kungreat.bbs.security.ImageFilter;
import cn.kungreat.bbs.security.MyUserDetails;
import cn.kungreat.bbs.security.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetails myUserDetails;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private SuccessHandler successHandler;
    @Autowired
    private FaliureHandler faliureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetails).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 接口层要获取认证对象的时候  不要在这里放行 这里 不会封装认证对象过来
        web.ignoring().antMatchers("/favicon.ico","/register.html","/home.html",
                "/address.html","/userImg/**","/css/**","/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // by default uses a Bean by the name of corsConfigurationSource
                // if Spring MVC is on classpath and no CorsConfigurationSource is provided,
                // Spring Security will use CORS configuration provided to Spring MVC
                .cors().and().csrf().disable()
                .addFilterBefore(new ImageFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/index","/register",
                "/image").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/index").loginProcessingUrl("/defaultLogin")
                .successHandler(successHandler)
                .failureHandler(faliureHandler)
                .and()
                .logout().logoutUrl("/clearAll").clearAuthentication(true)
                .invalidateHttpSession(true).deleteCookies("JSESSIONID","remember-me")
                .logoutSuccessUrl("/index");

    }
}