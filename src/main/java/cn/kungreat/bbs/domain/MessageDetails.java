package cn.kungreat.bbs.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MessageDetails {
    private Long id;

    private String account;

    private String messageDetails;

    private String imgPath;

    private Long messageId;

    private Date publishTime;

    private Integer isMain=0;

    public void setMessageDetails(String messageDetails) {
        this.messageDetails = messageDetails.trim();
    }

    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(messageDetails)){
            builder.append("信息不能为空");
        }
        if(messageId == null){
            builder.append("主信息ID不能为空");
        }
        if(messageDetails.length() > 166){
            builder.append("信息长度过大");
        }
        return builder.toString();
    }

}