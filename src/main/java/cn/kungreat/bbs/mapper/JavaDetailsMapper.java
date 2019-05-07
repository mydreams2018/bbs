package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.query.JavaDetailsQuery;

import java.util.List;

public interface JavaDetailsMapper {
    long insert(JavaDetails record);
    int deleteAll(JavaDetailsQuery query);
    int deleteByPrimaryId(Long PrimaryId);
    int updateByPrimaryId(JavaDetails record);
    int updateForPosts(JavaDetails record);
    JavaDetails selectByPrimaryId(long PrimaryId);
    JavaDetails selectReplyTimeEnd(Long postsId);

    long selectCount(JavaDetailsQuery query);
    List<JavaDetails> selectAll(JavaDetailsQuery query);
}