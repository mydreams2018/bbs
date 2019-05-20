package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
import java.util.List;

public interface AssemblerDetailsMapper {
    long insert(AssemblerDetails record);
    int deleteAll(AssemblerDetailsQuery query);
    int deleteByPrimaryId(Long PrimaryId);
    int updateByPrimaryId(AssemblerDetails record);
    int updateForPosts(AssemblerDetails record);
    AssemblerDetails selectByPrimaryId(long PrimaryId);
    AssemblerDetails selectReplyTimeEnd(Long postsId);

    long selectCount(AssemblerDetailsQuery query);
    List<AssemblerDetails> selectAll(AssemblerDetailsQuery query);

    List<Integer> selectReplyCount(AssemblerDetailsQuery query);
    List<AssemblerDetails> selectReply(AssemblerDetailsQuery query);
}