package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.domain.AssemblerDetailsRecord;
import cn.kungreat.bbs.mapper.AssemblerDetailsMapper;
import cn.kungreat.bbs.mapper.AssemblerDetailsRecordMapper;
import cn.kungreat.bbs.service.AssemblerDetailsRecordService;
import cn.kungreat.bbs.service.UserService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssemblerDetailsRecordServiceImpl implements AssemblerDetailsRecordService {
    @Autowired
    private AssemblerDetailsRecordMapper assemblerDetailsRecordMapper;
    @Autowired
    private AssemblerDetailsMapper assemblerDetailsMapper;
    @Autowired
    private UserService userService;
    @Transactional
    public int insert(AssemblerDetailsRecord record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        Assert.isTrue(assemblerDetailsRecordMapper.selectByPrimary(record)==null,"请不要重复操作");
        AssemblerDetails assemblerDetails = assemblerDetailsMapper.selectByPrimaryId(record.getAssemblerDetailsId());
        record.setPostsId(assemblerDetails.getPostsId());
        if(record.getState()){
            int i = userService.updateAccumulatePoints(5, assemblerDetails.getAccount());
            Assert.isTrue(i>0,"并发修改积分错误,请重试");
        }else{
            int i = userService.updateAccumulatePoints(-5, assemblerDetails.getAccount());
            Assert.isTrue(i>0,"并发修改积分错误,请重试");
        }
        return assemblerDetailsRecordMapper.insert(record);
    }

    @Override
    public List<AssemblerDetailsRecord> countRecord(Long detailsId) {
        Assert.isTrue(detailsId != null,"查询ID为空");
        return assemblerDetailsRecordMapper.countRecord(detailsId);
    }

    @Override
    public List<List<AssemblerDetailsRecord>> selectByDetails(List<AssemblerDetails> details) {
        List<List<AssemblerDetailsRecord>> lists = new ArrayList<>();
        for(int x=0; x<details.size();x++){
            lists.add(countRecord(details.get(x).getId()));
        }
        return lists;
    }

}
