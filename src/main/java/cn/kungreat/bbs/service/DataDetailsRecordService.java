package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.DataDetails;
import cn.kungreat.bbs.domain.DataDetailsRecord;

import java.util.List;

public interface DataDetailsRecordService {
    int insert(DataDetailsRecord record);
    List<DataDetailsRecord> countRecord(Long detailsId);
    List<List<DataDetailsRecord>> selectByDetails(List<DataDetails> details);
}
