package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.SendMessage;
import cn.kungreat.bbs.query.SendMessageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SendMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SendMessage record);

    SendMessage selectByPrimaryKey(Long id);
    SendMessage selectByUnique(@Param("from") String from, @Param("to") String to);
    List<SendMessage> selectAll(SendMessageQuery query);
    long selectCount(SendMessageQuery query);

    int updateByPrimaryKey(SendMessage record);
}