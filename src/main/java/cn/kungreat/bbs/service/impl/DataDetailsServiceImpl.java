package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.DataDetails;
import cn.kungreat.bbs.domain.DataPosts;
import cn.kungreat.bbs.mapper.*;
import cn.kungreat.bbs.query.DataDetailsQuery;
import cn.kungreat.bbs.service.DataDetailsService;
import cn.kungreat.bbs.service.DataPostsService;
import cn.kungreat.bbs.service.UserService;
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
public class DataDetailsServiceImpl implements DataDetailsService {
    @Autowired
    private DataDetailsMapper dataDetailsMapper;
    @Autowired
    private DataPostsMapper dataPostsMapper;
    @Autowired
    private DataPostsService dataPostsService;
    @Autowired
    private DataDetailsRecordMapper dataDetailsRecordMapper;
    @Autowired
    private UserService userService;
    @Transactional
    public long insert(DataDetails record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        record.setPublishTime(new Date());
        dataDetailsMapper.insert(record);
        int i = userService.updateAccumulatePoints(10, account);
        Assert.isTrue(i>0,"并发修改错误,请重新提交");
        DataPosts dataPosts = dataPostsMapper.selectByPrimaryKey(record.getPostsId());
        Assert.isTrue(dataPosts!=null,"数据异常");
        dataPosts.setReplyTimeEnd(new Date());
        dataPosts.setReplyTotal(dataPosts.getReplyTotal() + 1);
        return dataPostsMapper.updateByReply(dataPosts);
    }

    //  用户名AND主贴ID 删除指定用户下指定贴子所有回贴 并减掉回贴数量 要权限控制
    @Transactional
    public int deleteAll(DataDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getPostsId().toString())
                        && !StringUtils.isEmpty(query.getAccount()),"主贴ID-用户不能为空");
        int i = dataDetailsMapper.deleteAll(query);
        if(i > 0){
            subtractReply(i, query.getPostsId());
        }
        return i;
    }

    @Transactional
    public int deleteByPrimaryId(Long PrimaryId){
        DataDetails dataDetails = dataDetailsMapper.selectByPrimaryId(PrimaryId);
        Assert.isTrue(dataDetails!=null,"查询数据失败");
        if(dataDetails.getIsPosts() == 1){
            dataPostsService.deleteByPrimaryKey(dataDetails.getPostsId());
            dataDetailsRecordMapper.deleteByPostsId(dataDetails.getPostsId());
        }else{
            dataDetailsMapper.deleteByPrimaryId(PrimaryId);
            dataDetailsRecordMapper.deleteByDeatilsId(PrimaryId);
            subtractReply(1, dataDetails.getPostsId());
        }
        return 1;
    }

    private void subtractReply(int i, Long postsId) {
        DataPosts dataPosts = dataPostsMapper.selectByPrimaryKey(postsId);
        Assert.isTrue(dataPosts != null, "数据异常");
        DataDetails dataDetails = dataDetailsMapper.selectReplyTimeEnd(postsId);
        dataPosts.setReplyTotal(dataPosts.getReplyTotal() - i);
        dataPosts.setReplyTimeEnd(dataDetails.getPublishTime());
        dataPostsMapper.updateByReply(dataPosts);
    }

    @Transactional
    public int updateByPrimaryId(DataDetails record) {
        Assert.isTrue(record.getId()!=null,"编辑的信息ID不能为空");
        DataDetails dataDetails = dataDetailsMapper.selectByPrimaryId(record.getId());
        Assert.isTrue(dataDetails!=null,"查询数据失败");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(dataDetails.getAccount()),
                "没有权限编辑别人的贴子");
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        dataDetails.setUpdateTime(new Date());
        dataDetails.setDetailData(record.getDetailData());
        return dataDetailsMapper.updateByPrimaryId(dataDetails);
    }

    @Override
    public DataDetails selectByPrimaryId(long PrimaryId) {
        DataDetails dataDetails = dataDetailsMapper.selectByPrimaryId(PrimaryId);
        Assert.isTrue(dataDetails!=null,"查询数据为空");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(dataDetails.getAccount()),
                "没有权限查看别人的贴子");
        return dataDetails;
    }

    @Override
    public QueryResult selectByPostsId(DataDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getPostsId().toString()),"查询必要条件为空");
        long count = dataDetailsMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = dataDetailsMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Override
    public QueryResult selectReply(DataDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getAccount()),"查询用户不能为空");
        long count = dataDetailsMapper.selectReplyCount(query).size();
        List list  = Collections.emptyList();
        if (count >  0){
            list = dataDetailsMapper.selectReply(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

}
