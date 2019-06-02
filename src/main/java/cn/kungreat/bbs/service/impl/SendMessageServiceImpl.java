package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.MessageDetails;
import cn.kungreat.bbs.domain.SendMessage;
import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.mapper.MessageDetailsMapper;
import cn.kungreat.bbs.mapper.SendMessageMapper;
import cn.kungreat.bbs.mapper.UserMapper;
import cn.kungreat.bbs.query.SendMessageQuery;
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
public class SendMessageServiceImpl implements SendMessageService {
    @Autowired
    private SendMessageMapper sendMessageMapper;
    @Autowired
    private MessageDetailsMapper messageDetailsMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        SendMessage message = sendMessageMapper.selectByPrimaryKey(id);
        Assert.isTrue(message!=null,"没有查询到相关信息");
        String contextName = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(contextName.equals(message.getFromAccount()),"不能删除别人的信息");
        sendMessageMapper.deleteByPrimaryKey(id);
        messageDetailsMapper.deleteByMessageId(id);
        return 1;
    }

    @Transactional
    public int insert(SendMessage record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        User user = userMapper.selectByPrimaryKey(record.getToAccount());
        Assert.isTrue(user!=null,"目标用户为空");
        String contextName = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(sendMessageMapper.selectByUnique(contextName,record.getToAccount())==null
                ,"已经存在和此用户的信息沟通,请打开详情页面发送信息");
        Assert.isTrue(!contextName.equals(user.getAccount()),"自已不能给自已发信息");
        Date date = new Date();
        record.setSendTime(date);
        record.setFromAccount(contextName);
        record.setToImg(user.getImg());
        record.setFromImg(userMapper.selectByPrimaryKey(contextName).getImg());
        sendMessageMapper.insert(record);
        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setAccount(contextName);
        messageDetails.setImgPath(record.getFromImg());
        messageDetails.setMessageDetails(record.getMessage());
        messageDetails.setMessageId(record.getId());
        messageDetails.setPublishTime(date);
        messageDetails.setIsMain(1);
        messageDetailsMapper.insert(messageDetails);
        return 1;
    }

    @Override
    public QueryResult list(SendMessageQuery query) {
        Assert.isTrue(StringUtils.isEmpty(query.validMessage()),query.validMessage());
        long count = sendMessageMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = sendMessageMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Override
    public SendMessage selectByPrimaryKey(Long id) {
        return sendMessageMapper.selectByPrimaryKey(id);
    }
}
