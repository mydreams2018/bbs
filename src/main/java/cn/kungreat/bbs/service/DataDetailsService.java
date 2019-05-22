package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.DataDetails;
import cn.kungreat.bbs.query.DataDetailsQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface DataDetailsService {
    long insert(DataDetails record);
    int deleteAll(DataDetailsQuery query);
    int deleteByPrimaryId(Long PrimaryId);
    int updateByPrimaryId(DataDetails record);
    DataDetails selectByPrimaryId(long PrimaryId);
    QueryResult selectByPostsId(DataDetailsQuery query);
    QueryResult selectReply(DataDetailsQuery query);
}
