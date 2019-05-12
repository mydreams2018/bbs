package cn.kungreat.bbs.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JavaDetailsRecord {
    private Long id;

    private Long javaDetailsId;

    private String account;

    private Boolean state=null;
    // 分组计算数据
    private Integer total;

    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(javaDetailsId.toString())){
            builder.append("贴子详情ID不能为空");
        }
        if(state == null){
            builder.append("状态不能为空");
        }
        return builder.toString();
    }
}