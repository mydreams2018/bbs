package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.PermissionMapping;
import cn.kungreat.bbs.mapper.PermissionMappingMapper;
import cn.kungreat.bbs.service.PermissionMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class PermissionMappingServiceImpl implements PermissionMappingService {
    @Autowired
    private PermissionMappingMapper permissionMappingMapper;
    @Value("#{'${user.manager}'.split(',')}")
    private List<String> manager;

    @Transactional
    public int insertBatch(List<PermissionMapping> record,String account) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Assert.isTrue(manager.contains(name),"没有权限访问");
        permissionMappingMapper.deleteByAccount(account);
        return  permissionMappingMapper.insertBatch(record);
    }

    @Override
    public List<PermissionMapping> selectAll(String account) {
        return permissionMappingMapper.selectAll(account);
    }
}
