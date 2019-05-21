package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.JavaDetailsRecord;
import cn.kungreat.bbs.mapper.JavaDetailsMapper;
import cn.kungreat.bbs.mapper.JavaDetailsRecordMapper;
import cn.kungreat.bbs.service.JavaDetailsRecordService;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
@Service
public class JavaDetailsRecordServiceImpl implements JavaDetailsRecordService {
    @Autowired
    private JavaDetailsRecordMapper javaDetailsRecordMapper;
    @Autowired
    private JavaDetailsMapper javaDetailsMapper;
    @Transactional
    public int insert(JavaDetailsRecord record) {
        Assert.isTrue(StringUtils.isEmpty(record.validMessage()),record.validMessage());
        String account = SecurityContextHolder.getContext().getAuthentication().getName();
        record.setAccount(account);
        Assert.isTrue(javaDetailsRecordMapper.selectByPrimary(record)==null,"请不要重复操作");
        record.setPostsId(javaDetailsMapper.selectByPrimaryId(record.getJavaDetailsId()).getPostsId());
        return javaDetailsRecordMapper.insert(record);
    }

    @Override
    public List<JavaDetailsRecord> countRecord(Long detailsId) {
        Assert.isTrue(detailsId != null,"查询ID为空");
        return javaDetailsRecordMapper.countRecord(detailsId);
    }

    @Override
    public List<List<JavaDetailsRecord>> selectByDetails(List<JavaDetails> details) {
        List<List<JavaDetailsRecord>> lists = new ArrayList<>();
        for(int x=0; x<details.size();x++){
            lists.add(countRecord(details.get(x).getId()));
        }
        return lists;
    }

}
