package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.MessageDetails;
import cn.kungreat.bbs.query.MessageDetailsQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface MessageDetailsService {
    int deleteByPrimaryKey(Long id);
    int insert(MessageDetails record);
    QueryResult list(MessageDetailsQuery query);
}
