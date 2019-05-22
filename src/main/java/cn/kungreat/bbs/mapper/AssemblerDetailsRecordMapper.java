package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.AssemblerDetailsRecord;

import java.util.List;

public interface AssemblerDetailsRecordMapper {
    int deleteByDetailsId(Long detailsId);
    int deleteByPostsId(Long postsId);
    int insert(AssemblerDetailsRecord record);
    AssemblerDetailsRecord selectByPrimary(AssemblerDetailsRecord record);
    List<AssemblerDetailsRecord> selectAll();
    List<AssemblerDetailsRecord> countRecord(long detailsId);

}