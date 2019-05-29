package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.PermissionMapping;

import java.util.List;

public interface PermissionMappingService {
    int insertBatch(List<String> record,String account);

    List<PermissionMapping> selectAll(String account);
}
