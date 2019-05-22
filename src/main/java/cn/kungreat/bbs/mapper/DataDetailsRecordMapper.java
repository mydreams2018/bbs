package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.DataDetailsRecord;
import java.util.List;

public interface DataDetailsRecordMapper {
    int deleteByDeatilsId(Long detailsId);
    int deleteByPostsId(Long detailsId);
    int insert(DataDetailsRecord record);
    DataDetailsRecord selectByPrimary(DataDetailsRecord record);
    List<DataDetailsRecord> selectAll();
    List<DataDetailsRecord> countRecord(long detailsId);
}