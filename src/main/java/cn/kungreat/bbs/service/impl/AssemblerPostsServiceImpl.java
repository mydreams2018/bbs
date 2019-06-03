package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.domain.AssemblerPosts;
import cn.kungreat.bbs.mapper.AssemblerDetailsMapper;
import cn.kungreat.bbs.mapper.AssemblerPostsMapper;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
import cn.kungreat.bbs.query.AssemblerPostsQuery;
import cn.kungreat.bbs.service.AssemblerDetailsService;
import cn.kungreat.bbs.service.AssemblerPostsService;
import cn.kungreat.bbs.service.UserService;
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
public class AssemblerPostsServiceImpl implements AssemblerPostsService {
    @Autowired
    private UserService userService;
    @Autowired
    private AssemblerPostsMapper assemblerPostsMapper;
    @Autowired
    private AssemblerDetailsMapper assemblerDetailsMapper;
    @Autowired
    private AssemblerDetailsService assemblerDetailsService;
    //要权限控制
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        assemblerPostsMapper.deleteByPrimaryKey(id);
        AssemblerDetailsQuery assemblerDetailsQuery = new AssemblerDetailsQuery();
        assemblerDetailsQuery.setPostsId(id);
        return assemblerDetailsMapper.deleteAll(assemblerDetailsQuery);
    }

    //要权限控制
    @Transactional
    public int deleteByAccount(String account) {
        assemblerPostsMapper.deleteByAccount(account);
        AssemblerDetailsQuery assemblerDetailsQuery = new AssemblerDetailsQuery();
        assemblerDetailsQuery.setAccount(account);
        return assemblerDetailsMapper.deleteAll(assemblerDetailsQuery);
    }

    @Transactional
    public int deleteByOwner(Long id) {
        AssemblerPosts assemblerPosts = assemblerPostsMapper.selectByPrimaryKey(id);
        Assert.isTrue(assemblerPosts!=null,"此ID数据不存在");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(assemblerPosts.getAccount().equals(account),"不能删除别人发的贴");
        return deleteByPrimaryKey(id);
    }

    @Transactional
    public long insert(AssemblerPosts record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        Date date = new Date();
        record.setPublishTime(date);
        assemblerPostsMapper.insert(record);
        int i = userService.updateAccumulatePoints(30, account);
        Assert.isTrue(i>0,"并发修改积分错误,重测");
        AssemblerDetails assemblerDetails = new AssemblerDetails();
        assemblerDetails.setAccount(account);
        assemblerDetails.setPostsId(record.getId());
        assemblerDetails.setIsPosts(1);
        assemblerDetails.setPublishTime(date);
        assemblerDetails.setDetailData(record.getDetailData());
        return assemblerDetailsMapper.insert(assemblerDetails);
    }

    public AssemblerPosts selectByPrimaryKey(Long id) {
        AssemblerPosts assemblerPosts = assemblerPostsMapper.selectByPrimaryKey(id);
        Assert.isTrue(assemblerPosts!=null,"没有查询到数据");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(assemblerPosts.getAccount()),"必须是同一个用户才能查看编辑");
        return assemblerPosts;
    }

    public QueryResult selectAll(AssemblerPostsQuery query) {
        long count = assemblerPostsMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = assemblerPostsMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Transactional
    public int updateByPrimaryKey(AssemblerPosts record) {
        Assert.isTrue(record.getId()!=null,"查询ID不能为空");
        AssemblerPosts assemblerPosts = assemblerPostsMapper.selectByPrimaryKey(record.getId());
        Assert.isTrue(assemblerPosts!=null,"查询ID不能为空");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(assemblerPosts.getAccount()),"没有权限修改别人的贴子");
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        Date date = new Date();
        assemblerPosts.setCategory(record.getCategory());
        assemblerPosts.setPostsName(record.getPostsName());
        assemblerPostsMapper.updateByPrimaryKey(assemblerPosts);
        AssemblerDetails assemblerDetails = new AssemblerDetails();
        assemblerDetails.setAccount(account);
        assemblerDetails.setUpdateTime(date);
        assemblerDetails.setPostsId(assemblerPosts.getId());
        assemblerDetails.setDetailData(record.getDetailData());
        return assemblerDetailsMapper.updateForPosts(assemblerDetails);
    }

    @Override
    public QueryResult selectReplyByAccount(AssemblerDetailsQuery query) {
        QueryResult queryResult = assemblerDetailsService.selectReply(query);
        List datas = queryResult.getDatas();
        List<AssemblerPosts>  list = new ArrayList<>();
        if(datas != null && datas.size() > 0){
            List<AssemblerDetails> assemblerDetails =  datas;
            for (int x=0 ;x < assemblerDetails.size();x++){
                AssemblerPosts assemblerPosts = assemblerPostsMapper.selectByPrimaryKey(assemblerDetails.get(x).getPostsId());
                list.add(assemblerPosts);
            }
        }
        queryResult.setDatas(list);
        return queryResult;
    }
}
