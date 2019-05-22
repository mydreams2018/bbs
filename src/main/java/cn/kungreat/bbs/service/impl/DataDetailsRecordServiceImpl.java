package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.DataDetails;
import cn.kungreat.bbs.domain.DataDetailsRecord;
import cn.kungreat.bbs.mapper.DataDetailsMapper;
import cn.kungreat.bbs.mapper.DataDetailsRecordMapper;
import cn.kungreat.bbs.service.DataDetailsRecordService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataDetailsRecordServiceImpl implements DataDetailsRecordService {
    @Autowired
    private DataDetailsRecordMapper dataDetailsRecordMapper;
    @Autowired
    private DataDetailsMapper dataDetailsMapper;
    @Transactional
    public int insert(DataDetailsRecord record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        Assert.isTrue(dataDetailsRecordMapper.selectByPrimary(record)==null,"请不要重复操作");
        record.setPostsId(dataDetailsMapper.selectByPrimaryId(record.getDetailsId()).getPostsId());
        return dataDetailsRecordMapper.insert(record);
    }

    @Override
    public List<DataDetailsRecord> countRecord(Long detailsId) {
        Assert.isTrue(detailsId != null,"查询ID为空");
        return dataDetailsRecordMapper.countRecord(detailsId);
    }

    @Override
    public List<List<DataDetailsRecord>> selectByDetails(List<DataDetails> details) {
        List<List<DataDetailsRecord>> lists = new ArrayList<>();
        for(int x=0; x<details.size();x++){
            lists.add(countRecord(details.get(x).getId()));
        }
        return lists;
    }

}
