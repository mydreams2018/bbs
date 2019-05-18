package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.query.JavaDetailsQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface JavaDetailsService {
    long insert(JavaDetails record);
    int deleteAll(JavaDetailsQuery query);
    int deleteByPrimaryId(Long PrimaryId);
    int updateByPrimaryId(JavaDetails record);
    JavaDetails selectByPrimaryId(long PrimaryId);
    QueryResult selectByPostsId(JavaDetailsQuery query);
    QueryResult selectReply(JavaDetailsQuery query);
}
