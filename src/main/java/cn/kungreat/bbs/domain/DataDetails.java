package cn.kungreat.bbs.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DataDetails {
    private Long id;

    private String account;

    private Date publishTime;

    private Date updateTime;

    private String detailData;

    private Long postsId;

    private Integer isPosts = 0;
    // 统计字段
    private Integer replyTotal;
    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(postsId.toString())){
            builder.append("贴子ID不能为空");
        }
        if(StringUtils.isEmpty(detailData)){
            builder.append("贴子内容不能为空");
        }
        if(detailData.getBytes().length > 3145728){
            builder.append("贴子内容长度太大,请不要放太大图片");
        }
        return builder.toString();
    }
}