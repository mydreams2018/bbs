package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.MessageDetails;
import cn.kungreat.bbs.domain.SendMessage;
import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.mapper.MessageDetailsMapper;
import cn.kungreat.bbs.mapper.UserMapper;
import cn.kungreat.bbs.query.MessageDetailsQuery;
import cn.kungreat.bbs.service.MessageDetailsService;
import cn.kungreat.bbs.service.SendMessageService;
import cn.kungreat.bbs.vo.QueryResult;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MessageDetailsServiceImpl implements MessageDetailsService {
    @Autowired
    private MessageDetailsMapper messageDetailsMapper;
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public int deleteByPrimaryKey(Long id) {
        MessageDetails messageDetails = messageDetailsMapper.selectByPrimaryKey(id);
        Assert.isTrue(messageDetails != null,"没有此信息");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(name.equals(messageDetails.getAccount()),"不能删除别人的信息");
        if(messageDetails.getIsMain() == 1){
            sendMessageService.deleteByPrimaryKey(messageDetails.getMessageId());
        }else{
            messageDetailsMapper.deleteByPrimaryKey(id);
        }
        return 1;
    }

    @Transactional
    public int insert(MessageDetails record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        SendMessage message = sendMessageService.selectByPrimaryKey(record.getMessageId());
        Assert.isTrue(message!=null,"没有此ID信息");
        Assert.isTrue(name.equals(message.getFromAccount())||name.equals(message.getToAccount()),"没有权限操作别人的信息");
        User user = userMapper.selectByPrimaryKey(name);
        record.setAccount(name);
        record.setPublishTime(new Date());
        record.setImgPath(user.getImg());
        return messageDetailsMapper.insert(record);
    }

    @Override
    public QueryResult list(MessageDetailsQuery query) {
        Assert.isTrue(query.getMessageId() != null,"ID为空");
        SendMessage message = sendMessageService.selectByPrimaryKey(query.getMessageId());
        Assert.isTrue(message != null,"查询信息为空");
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(name.equals(message.getFromAccount()) || name.equals(message.getToAccount()),"没有权限查看此信息");
        long count = messageDetailsMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = messageDetailsMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }
}
