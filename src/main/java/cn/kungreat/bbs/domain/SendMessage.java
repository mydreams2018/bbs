package cn.kungreat.bbs.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SendMessage {
    private Long id;

    private String fromAccount;

    private String toAccount;

    private String message;

    private Date sendTime;

    private String fromImg;
    private String toImg;

    public void setMessage(String message) {
        this.message = message.trim();
    }

    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(toAccount)){
            builder.append("目标用户不能为空");
        }
        if(StringUtils.isEmpty(message)){
            builder.append("信息内容不能为空");
        }
        if(message.length() > 166){
            builder.append("信息长度过大");
        }
        return builder.toString();
    }
}