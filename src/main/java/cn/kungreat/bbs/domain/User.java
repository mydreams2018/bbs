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

    private Byte state=1;

    private String email;

    private String description;
    private String originFrom="default";
    private Date registerTime;
    private String icon="/userImg/bbsIcon/defaultMember.gif";
    private Integer registerYear ;
    private Integer accumulatePoints=0;
    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(account) || account.length() < 6){
            builder.append("用户名不能为空-不能小于6位数");
        }
        if(StringUtils.isEmpty(password) || password.length() < 6){
            builder.append("密码不能为空-不能小于6位数");
        }
        byte[] bytes = account.getBytes();
        for(int x =0;x<bytes.length;x++){
            if(bytes[x] > 47 && bytes[x] < 58 || bytes[x] > 64 && bytes[x] < 91
                    || bytes[x] > 96 && bytes[x] < 123){

            }else{
                builder.append("账户只能是字母和数字");
                break;
            }
        }
        return builder.toString();
    }
}