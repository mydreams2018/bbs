package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.MessageDetails;
import cn.kungreat.bbs.query.MessageDetailsQuery;

import java.util.List;

public interface MessageDetailsMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByMessageId(long messageId);
    int insert(MessageDetails record);

    MessageDetails selectByPrimaryKey(Long id);

    List<MessageDetails> selectAll(MessageDetailsQuery query);
    long selectCount(MessageDetailsQuery query);

    int updateByPrimaryKey(MessageDetails record);
}