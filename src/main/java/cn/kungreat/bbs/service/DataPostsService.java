package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.DataPosts;
import cn.kungreat.bbs.query.DataDetailsQuery;
import cn.kungreat.bbs.query.DataPostsQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface DataPostsService {
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(String account);

    int deleteByOwner(Long id);

    long insert(DataPosts record);

    DataPosts selectByPrimaryKey(Long id);

    QueryResult selectAll(DataPostsQuery query);

    int updateByPrimaryKey(DataPosts record);
    QueryResult selectReplyByAccount(DataDetailsQuery query);
}
