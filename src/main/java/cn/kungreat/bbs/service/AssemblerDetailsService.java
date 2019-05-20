package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface AssemblerDetailsService {
    long insert(AssemblerDetails record);
    int deleteAll(AssemblerDetailsQuery query);
    int deleteByPrimaryId(Long PrimaryId);
    int updateByPrimaryId(AssemblerDetails record);
    AssemblerDetails selectByPrimaryId(long PrimaryId);
    QueryResult selectByPostsId(AssemblerDetailsQuery query);
    QueryResult selectReply(AssemblerDetailsQuery query);
}
