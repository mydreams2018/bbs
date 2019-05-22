package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.DataDetails;
import cn.kungreat.bbs.domain.DataPosts;
import cn.kungreat.bbs.mapper.DataDetailsMapper;
import cn.kungreat.bbs.mapper.DataPostsMapper;
import cn.kungreat.bbs.query.DataDetailsQuery;
import cn.kungreat.bbs.query.DataPostsQuery;
import cn.kungreat.bbs.service.DataDetailsService;
import cn.kungreat.bbs.service.DataPostsService;
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
public class DataPostsServiceImpl implements DataPostsService {

    @Autowired
    private DataPostsMapper dataPostsMapper;
    @Autowired
    private DataDetailsMapper dataDetailsMapper;
    @Autowired
    private DataDetailsService dataDetailsService;
    //要权限控制
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        dataPostsMapper.deleteByPrimaryKey(id);
        DataDetailsQuery dataDetailsQuery = new DataDetailsQuery();
        dataDetailsQuery.setPostsId(id);
        return dataDetailsMapper.deleteAll(dataDetailsQuery);
    }

    //要权限控制
    @Transactional
    public int deleteByAccount(String account) {
        dataPostsMapper.deleteByAccount(account);
        DataDetailsQuery dataDetailsQuery = new DataDetailsQuery();
        dataDetailsQuery.setAccount(account);
        return dataDetailsMapper.deleteAll(dataDetailsQuery);
    }

    @Transactional
    public int deleteByOwner(Long id) {
        DataPosts dataPosts = dataPostsMapper.selectByPrimaryKey(id);
        Assert.isTrue(dataPosts!=null,"此ID数据不存在");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(dataPosts.getAccount().equals(account),"不能删除别人发的贴");
        return deleteByPrimaryKey(id);
    }

    @Transactional
    public long insert(DataPosts record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        Date date = new Date();
        record.setPublishTime(date);
        dataPostsMapper.insert(record);
        DataDetails dataDetails = new DataDetails();
        dataDetails.setAccount(account);
        dataDetails.setPostsId(record.getId());
        dataDetails.setIsPosts(1);
        dataDetails.setPublishTime(date);
        dataDetails.setDetailData(record.getDetailData());
        return dataDetailsMapper.insert(dataDetails);
    }

    public DataPosts selectByPrimaryKey(Long id) {
        DataPosts dataPosts = dataPostsMapper.selectByPrimaryKey(id);
        Assert.isTrue(dataPosts!=null,"没有查询到数据");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(dataPosts.getAccount()),"必须是同一个用户才能查看编辑");
        return dataPosts;
    }

    public QueryResult selectAll(DataPostsQuery query) {
        long count = dataPostsMapper.selectCount(query);
        List list  = Collections.emptyList();
        if (count >  0){
            list = dataPostsMapper.selectAll(query);
        }
        query.setData(count,query.getPageSize(),query.getCurrentPage());
        QueryResult  result = new QueryResult();
        result.setDatas(list);
        result.setPage(query);
        return result;
    }

    @Transactional
    public int updateByPrimaryKey(DataPosts record) {
        Assert.isTrue(record.getId()!=null,"查询ID不能为空");
        DataPosts dataPosts = dataPostsMapper.selectByPrimaryKey(record.getId());
        Assert.isTrue(dataPosts!=null,"查询ID不能为空");
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(account.equals(dataPosts.getAccount()),"没有权限修改别人的贴子");
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        Date date = new Date();
        dataPosts.setCategory(record.getCategory());
        dataPosts.setPostsName(record.getPostsName());
        dataPostsMapper.updateByPrimaryKey(dataPosts);
        DataDetails dataDetails = new DataDetails();
        dataDetails.setAccount(account);
        dataDetails.setUpdateTime(date);
        dataDetails.setPostsId(dataPosts.getId());
        dataDetails.setDetailData(record.getDetailData());
        return dataDetailsMapper.updateForPosts(dataDetails);
    }

    @Override
    public QueryResult selectReplyByAccount(DataDetailsQuery query) {
        QueryResult queryResult = dataDetailsService.selectReply(query);
        List datas = queryResult.getDatas();
        List<DataPosts>  list = new ArrayList<>();
        if(datas != null && datas.size() > 0){
            List<DataDetails> dataDetails =  datas;
            for (int x=0 ;x < dataDetails.size();x++){
                DataPosts dataPosts = dataPostsMapper.selectByPrimaryKey(dataDetails.get(x).getPostsId());
                list.add(dataPosts);
            }
        }
        queryResult.setDatas(list);
        return queryResult;
    }
}
