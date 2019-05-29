package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.Permission;
import cn.kungreat.bbs.mapper.PermissionMapper;
import cn.kungreat.bbs.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<String> selectPermissions(String account) {
        return permissionMapper.selectPermissions(account);
    }

    @Override
    public List<Permission> selectAll() {
        return permissionMapper.selectAll();
    }
}
