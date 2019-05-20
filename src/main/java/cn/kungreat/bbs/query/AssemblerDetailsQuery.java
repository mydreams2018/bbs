package cn.kungreat.bbs.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssemblerDetailsQuery extends Paging {
    private Long id;
    private String account;
    private Long postsId;
    private String orderField="publish_time";

    public void setOrderField(String orderField) {
        if("reply_total".equals(orderField)){
            this.orderField = orderField;
        }

    }
}
