package cn.kungreat.bbs.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JavaPostsQuery extends Paging{
    private Integer category = 0;
    private String orderField = "publish_time";
    private String searchKeyword = null;
    private String account = null;

    public void setOrderField(String orderField) {
        if("reply_total".equals(orderField)){
            this.orderField = orderField;
        }
    }
}
