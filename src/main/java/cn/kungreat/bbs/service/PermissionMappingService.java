package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.PermissionMapping;

import java.util.List;

public interface PermissionMappingService {
    int insertBatch(List<PermissionMapping> record,String account);

    List<PermissionMapping> selectAll(String account);
}
