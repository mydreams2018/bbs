package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.JavaDetailsRecord;
import java.util.List;

public interface JavaDetailsRecordMapper {

    int insert(JavaDetailsRecord record);
    JavaDetailsRecord selectByPrimary(JavaDetailsRecord record);
    List<JavaDetailsRecord> selectAll();
    List<JavaDetailsRecord> countRecord(long detailsId);

}