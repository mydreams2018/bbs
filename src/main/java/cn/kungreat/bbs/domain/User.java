package cn.kungreat.bbs.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User {
    private Long id;

    private String account;

    private String password;

    private String alias;

    private Long phone;

    private String img="/userImg/default.jpg";

    private Boolean isVip;

    private Byte state;

    private String email;

    private String description;

    private Date registerTime;
    private String icon="/userImg/bbsIcon/defaultMember.gif";

    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(account) || account.length() < 6){
            builder.append("用户名不能为空-不能小于6位数");
        }
        if(StringUtils.isEmpty(password) || password.length() < 6){
            builder.append("密码不能为空-不能小于6位数");
        }
        return builder.toString();
    }
}