package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.JavaDetailsRecord;

import java.util.List;

public interface JavaDetailsRecordService {
    int insert(JavaDetailsRecord record);
    List<JavaDetailsRecord> countRecord(Long detailsId);

    List<List<JavaDetailsRecord>> selectByDetails(List<JavaDetails> details);
}
