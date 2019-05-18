package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.mapper.JavaDetailsMapper;
import cn.kungreat.bbs.mapper.JavaPostsMapper;
import cn.kungreat.bbs.query.JavaDetailsQuery;
import cn.kungreat.bbs.query.JavaPostsQuery;
import cn.kungreat.bbs.service.JavaDetailsService;
import cn.kungreat.bbs.service.JavaPostsService;
import cn.kungreat.bbs.vo.QueryResult;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class JavaPostsServiceImpl implements JavaPostsService {

    @Autowired
    private JavaPostsMapper javaPostsMapper;
    @Autowired
    private JavaDetailsMapper javaDetailsMapper;
    @Autowired
    private JavaDetailsService javaDetailsService;
    //要权限控制
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        javaPostsMapper.deleteByPrimaryKey(id);
        JavaDetailsQuery javaDetailsQuery = new JavaDetailsQuery();
        javaDetailsQuery.setPostsId(id);
        return javaDetailsMapper.deleteAll(javaDetailsQuery);
    }

    //要权限控制
    @Transactional
    public int deleteByAccount(String account) {
        javaPostsMapper.deleteByAccount(account);
        JavaDetailsQuery javaDetailsQuery = new JavaDetailsQuery();
        javaDetailsQuery.setAccount(account);
        return javaDetailsMapper.deleteAll(javaDetailsQuery);
    }

    @Transactional
    public int deleteByOwner(Long id) {
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(id);
        Assert.isTrue(javaPosts!=null,"此ID数据不存在");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(javaPosts.getAccount().equals(account),"不能删除别人发的贴");
        return deleteByPrimaryKey(id);
    }

    @Transactional
    public long insert(JavaPosts record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        Date date = new Date();
        record.setPublishTime(date);
        javaPostsMapper.insert(record);
        JavaDetails javaDetails = new JavaDetails();
        javaDetails.setAccount(account);
        javaDetails.setPostsId(record.getId());
        javaDetails.setIsPosts(1);
        javaDetails.setPublishTime(date);
        javaDetails.setDetailData(record.getDetailData());
        return javaDetailsMapper.insert(javaDetails);
    }

    public JavaPosts selectByPrimaryKey(Long id) {
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(id);
        Assert.isTrue(javaPosts!=null,"没有查询到数据");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(javaPosts.getAccount()),"必须是同一个用户才能查看编辑");
        return javaPosts;
    }

    public QueryResult selectAll(JavaPostsQuery query) {
        long count = javaPostsMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = javaPostsMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Transactional
    public int updateByPrimaryKey(JavaPosts record) {
        Assert.isTrue(record.getId()!=null,"查询ID不能为空");
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(record.getId());
        Assert.isTrue(javaPosts!=null,"查询ID不能为空");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(javaPosts.getAccount()),"没有权限修改别人的贴子");
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        Date date = new Date();
        javaPosts.setCategory(record.getCategory());
        javaPosts.setPostsName(record.getPostsName());
        javaPostsMapper.updateByPrimaryKey(javaPosts);
        JavaDetails javaDetails = new JavaDetails();
        javaDetails.setAccount(account);
        javaDetails.setUpdateTime(date);
        javaDetails.setPostsId(javaPosts.getId());
        javaDetails.setDetailData(record.getDetailData());
        return javaDetailsMapper.updateForPosts(javaDetails);
    }

    @Override
    public QueryResult selectReplyByAccount(JavaDetailsQuery query) {
        QueryResult queryResult = javaDetailsService.selectReply(query);
        List datas = queryResult.getDatas();
        List<JavaPosts>  list = new ArrayList<>();
        if(datas != null && datas.size() > 0){
            List<JavaDetails> javaDetails =  datas;
            for (int x=0 ;x < javaDetails.size();x++){
                JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(javaDetails.get(x).getPostsId());
                list.add(javaPosts);
            }
        }
        queryResult.setDatas(list);
        return queryResult;
    }
}
