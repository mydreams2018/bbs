package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.query.JavaPostsQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface JavaPostsService {
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(String account);

    int deleteByOwner(Long id);

    long insert(JavaPosts record);

    JavaPosts selectByPrimaryKey(Long id);

    QueryResult selectAll(JavaPostsQuery query);

    int updateByPrimaryKey(JavaPosts record);
}
