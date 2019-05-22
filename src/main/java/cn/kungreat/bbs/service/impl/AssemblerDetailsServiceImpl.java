package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.domain.AssemblerPosts;
import cn.kungreat.bbs.mapper.AssemblerDetailsMapper;
import cn.kungreat.bbs.mapper.AssemblerDetailsRecordMapper;
import cn.kungreat.bbs.mapper.AssemblerPostsMapper;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
import cn.kungreat.bbs.service.AssemblerDetailsService;
import cn.kungreat.bbs.service.AssemblerPostsService;
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
public class AssemblerDetailsServiceImpl implements AssemblerDetailsService {
    @Autowired
    private AssemblerDetailsMapper assemblerDetailsMapper;
    @Autowired
    private AssemblerPostsMapper assemblerPostsMapper;
    @Autowired
    private AssemblerDetailsRecordMapper assemblerDetailsRecordMapper;
    @Autowired
    private AssemblerPostsService assemblerPostsService;

    @Transactional
    public long insert(AssemblerDetails record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        record.setPublishTime(new Date());
        assemblerDetailsMapper.insert(record);
        AssemblerPosts assemblerPosts = assemblerPostsMapper.selectByPrimaryKey(record.getPostsId());
        Assert.isTrue(assemblerPosts!=null,"数据异常");
        assemblerPosts.setReplyTimeEnd(new Date());
        assemblerPosts.setReplyTotal(assemblerPosts.getReplyTotal() + 1);
        return assemblerPostsMapper.updateByReply(assemblerPosts);
    }

    //  用户名AND主贴ID 删除指定用户下指定贴子所有回贴 并减掉回贴数量 要权限控制
    @Transactional
    public int deleteAll(AssemblerDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getPostsId().toString())
                        && !StringUtils.isEmpty(query.getAccount()),"主贴ID-用户不能为空");
        int i = assemblerDetailsMapper.deleteAll(query);
        if(i > 0){
            subtractReply(i, query.getPostsId());
        }
        return i;
    }

    @Transactional
    public int deleteByPrimaryId(Long PrimaryId){
        AssemblerDetails assemblerDetails = assemblerDetailsMapper.selectByPrimaryId(PrimaryId);
        Assert.isTrue(assemblerDetails!=null,"查询数据失败");
        if(assemblerDetails.getIsPosts() == 1){
            assemblerPostsService.deleteByPrimaryKey(assemblerDetails.getPostsId());
            assemblerDetailsRecordMapper.deleteByPostsId(assemblerDetails.getPostsId());
        }else{
            assemblerDetailsMapper.deleteByPrimaryId(PrimaryId);
            assemblerDetailsRecordMapper.deleteByDetailsId(PrimaryId);
            subtractReply(1, assemblerDetails.getPostsId());
        }
        return 1;
    }

    private void subtractReply(int i, Long postsId) {
        AssemblerPosts assemblerPosts = assemblerPostsMapper.selectByPrimaryKey(postsId);
        Assert.isTrue(assemblerPosts != null, "数据异常");
        AssemblerDetails assemblerDetails = assemblerDetailsMapper.selectReplyTimeEnd(postsId);
        assemblerPosts.setReplyTotal(assemblerPosts.getReplyTotal() - i);
        assemblerPosts.setReplyTimeEnd(assemblerDetails.getPublishTime());
        assemblerPostsMapper.updateByReply(assemblerPosts);
    }

    @Transactional
    public int updateByPrimaryId(AssemblerDetails record) {
        Assert.isTrue(record.getId()!=null,"编辑的信息ID不能为空");
        AssemblerDetails assemblerDetails = assemblerDetailsMapper.selectByPrimaryId(record.getId());
        Assert.isTrue(assemblerDetails!=null,"查询数据失败");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(assemblerDetails.getAccount()),
                "没有权限编辑别人的贴子");
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        assemblerDetails.setUpdateTime(new Date());
        assemblerDetails.setDetailData(record.getDetailData());
        return assemblerDetailsMapper.updateByPrimaryId(assemblerDetails);
    }

    @Override
    public AssemblerDetails selectByPrimaryId(long PrimaryId) {
        AssemblerDetails assemblerDetails = assemblerDetailsMapper.selectByPrimaryId(PrimaryId);
        Assert.isTrue(assemblerDetails!=null,"查询数据为空");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(assemblerDetails.getAccount()),
                "没有权限查看别人的贴子");
        return assemblerDetails;
    }

    @Override
    public QueryResult selectByPostsId(AssemblerDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getPostsId().toString()),"查询必要条件为空");
        long count = assemblerDetailsMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = assemblerDetailsMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Override
    public QueryResult selectReply(AssemblerDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getAccount()),"查询用户不能为空");
        long count = assemblerDetailsMapper.selectReplyCount(query).size();
        List list  = Collections.emptyList();
        if (count >  0){
            list = assemblerDetailsMapper.selectReply(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

}
