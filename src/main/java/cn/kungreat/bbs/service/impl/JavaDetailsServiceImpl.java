package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.mapper.JavaDetailsMapper;
import cn.kungreat.bbs.mapper.JavaDetailsRecordMapper;
import cn.kungreat.bbs.mapper.JavaPostsMapper;
import cn.kungreat.bbs.query.JavaDetailsQuery;
import cn.kungreat.bbs.service.JavaDetailsService;
import cn.kungreat.bbs.service.JavaPostsService;
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
public class JavaDetailsServiceImpl implements JavaDetailsService {
    @Autowired
    private JavaDetailsMapper javaDetailsMapper;
    @Autowired
    private JavaPostsMapper javaPostsMapper;
    @Autowired
    private JavaPostsService javaPostsService;
    @Autowired
    private JavaDetailsRecordMapper javaDetailsRecordMapper;
    @Transactional
    public long insert(JavaDetails record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        record.setPublishTime(new Date());
        javaDetailsMapper.insert(record);
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(record.getPostsId());
        Assert.isTrue(javaPosts!=null,"数据异常");
        javaPosts.setReplyTimeEnd(new Date());
        javaPosts.setReplyTotal(javaPosts.getReplyTotal() + 1);
        return javaPostsMapper.updateByReply(javaPosts);
    }

    //  用户名AND主贴ID 删除指定用户下指定贴子所有回贴 并减掉回贴数量 要权限控制
    @Transactional
    public int deleteAll(JavaDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getPostsId().toString())
                        && !StringUtils.isEmpty(query.getAccount()),"主贴ID-用户不能为空");
        int i = javaDetailsMapper.deleteAll(query);
        if(i > 0){
            subtractReply(i, query.getPostsId());
        }
        return i;
    }

    @Transactional
    public int deleteByPrimaryId(Long PrimaryId){
        JavaDetails javaDetails = javaDetailsMapper.selectByPrimaryId(PrimaryId);
        Assert.isTrue(javaDetails!=null,"查询数据失败");
        if(javaDetails.getIsPosts() == 1){
            javaPostsService.deleteByPrimaryKey(javaDetails.getPostsId());
            javaDetailsRecordMapper.deleteByPostsId(javaDetails.getPostsId());
        }else{
            javaDetailsMapper.deleteByPrimaryId(PrimaryId);
            javaDetailsRecordMapper.deleteByDeatilsId(PrimaryId);
            subtractReply(1, javaDetails.getPostsId());
        }
        return 1;
    }

    private void subtractReply(int i, Long postsId) {
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(postsId);
        Assert.isTrue(javaPosts != null, "数据异常");
        JavaDetails javaDetails = javaDetailsMapper.selectReplyTimeEnd(postsId);
        javaPosts.setReplyTotal(javaPosts.getReplyTotal() - i);
        javaPosts.setReplyTimeEnd(javaDetails.getPublishTime());
        javaPostsMapper.updateByReply(javaPosts);
    }

    @Transactional
    public int updateByPrimaryId(JavaDetails record) {
        Assert.isTrue(record.getId()!=null,"编辑的信息ID不能为空");
        JavaDetails javaDetails = javaDetailsMapper.selectByPrimaryId(record.getId());
        Assert.isTrue(javaDetails!=null,"查询数据失败");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(javaDetails.getAccount()),
                "没有权限编辑别人的贴子");
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        javaDetails.setUpdateTime(new Date());
        javaDetails.setDetailData(record.getDetailData());
        return javaDetailsMapper.updateByPrimaryId(javaDetails);
    }

    @Override
    public JavaDetails selectByPrimaryId(long PrimaryId) {
        JavaDetails javaDetails = javaDetailsMapper.selectByPrimaryId(PrimaryId);
        Assert.isTrue(javaDetails!=null,"查询数据为空");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(javaDetails.getAccount()),
                "没有权限查看别人的贴子");
        return javaDetails;
    }

    @Override
    public QueryResult selectByPostsId(JavaDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getPostsId().toString()),"查询必要条件为空");
        long count = javaDetailsMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = javaDetailsMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Override
    public QueryResult selectReply(JavaDetailsQuery query) {
        Assert.isTrue(!StringUtils.isEmpty(query.getAccount()),"查询用户不能为空");
        long count = javaDetailsMapper.selectReplyCount(query).size();
        List list  = Collections.emptyList();
        if (count >  0){
            list = javaDetailsMapper.selectReply(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

}
