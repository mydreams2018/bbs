package cn.kungreat.bbs.service;

import java.util.List;

public interface PermissionService {
    List<String> selectPermissions(String account);
}
