package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.DataDetails;
import cn.kungreat.bbs.query.DataDetailsQuery;
import java.util.List;

public interface DataDetailsMapper {
    long insert(DataDetails record);
    int deleteAll(DataDetailsQuery query);
    int deleteByPrimaryId(Long PrimaryId);
    int updateByPrimaryId(DataDetails record);
    int updateForPosts(DataDetails record);
    DataDetails selectByPrimaryId(long PrimaryId);
    DataDetails selectReplyTimeEnd(Long postsId);

    long selectCount(DataDetailsQuery query);
    List<DataDetails> selectAll(DataDetailsQuery query);

    List<Integer> selectReplyCount(DataDetailsQuery query);
    List<DataDetails> selectReply(DataDetailsQuery query);
}