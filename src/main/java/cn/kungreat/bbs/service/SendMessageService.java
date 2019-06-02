package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.SendMessage;
import cn.kungreat.bbs.query.SendMessageQuery;
import cn.kungreat.bbs.vo.QueryResult;

public interface SendMessageService {
    int deleteByPrimaryKey(Long id);

    int insert(SendMessage record);

    QueryResult list(SendMessageQuery query);

    SendMessage selectByPrimaryKey(Long id);
}
