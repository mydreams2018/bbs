package cn.kungreat.bbs.query;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendMessageQuery extends Paging {
    private String fromAccount;
    private String toAccount;

    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(fromAccount) && StringUtils.isEmpty(toAccount)){
            builder.append("用户不能为空");
        }
        return builder.toString();
    }
}
