package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.domain.AssemblerDetailsRecord;

import java.util.List;

public interface AssemblerDetailsRecordService {
    int insert(AssemblerDetailsRecord record);
    List<AssemblerDetailsRecord> countRecord(Long detailsId);

    List<List<AssemblerDetailsRecord>> selectByDetails(List<AssemblerDetails> details);
}
