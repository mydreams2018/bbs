package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<String> selectPermissions(String account);
    List<Permission> selectAll();
}
