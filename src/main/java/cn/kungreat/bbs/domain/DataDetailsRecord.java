package cn.kungreat.bbs.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataDetailsRecord {
    private Long id;

    private Long detailsId;

    private String account;

    private Boolean state=null;
    private Long postsId;
    // 分组计算数据
    private Integer total;

    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(detailsId.toString())){
            builder.append("贴子详情ID不能为空");
        }
        if(state == null){
            builder.append("状态不能为空");
        }
        return builder.toString();
    }
}