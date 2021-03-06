package cn.kungreat.bbs.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserQuery extends Paging {
    private String groupField="origin_from";
    private String category;
    private String searchKeyword;

    public void setGroupField(String groupField) {
        if("register_year".equals(groupField)){
            this.groupField = groupField;
        }
    }
}
