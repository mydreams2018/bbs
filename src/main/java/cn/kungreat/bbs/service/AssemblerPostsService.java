package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.AssemblerPosts;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
import cn.kungreat.bbs.query.AssemblerPostsQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface AssemblerPostsService {
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(String account);

    int deleteByOwner(Long id);

    long insert(AssemblerPosts record);

    AssemblerPosts selectByPrimaryKey(Long id);

    QueryResult selectAll(AssemblerPostsQuery query);

    int updateByPrimaryKey(AssemblerPosts record);
    QueryResult selectReplyByAccount(AssemblerDetailsQuery query);
}
