package cn.kungreat.bbs.domain;

import com.alibaba.druid.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DataPosts {
    private Long id;

    private String account;

    private String postsName;

    private Byte category;

    private Date publishTime;

    private Date replyTimeEnd;

    private Integer replyTotal=0;
    //接收的富文本数据存在 Java_details 中
    private String detailData;
    public String validMessage(){
        StringBuilder builder = new StringBuilder();
        if(StringUtils.isEmpty(postsName)){
            builder.append("贴子主题不能为空");
        }
        if(StringUtils.isEmpty(category.toString())){
            builder.append("贴子类型不能为空");
        }
        if(StringUtils.isEmpty(detailData)){
            builder.append("贴子内容不能为空");
        }
        if(detailData.getBytes().length > 3145728){
            builder.append("贴子长度太大,请不要放太大图片");
        }
        return builder.toString();
    }
}