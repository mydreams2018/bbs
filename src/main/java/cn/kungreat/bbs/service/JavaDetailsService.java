package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.query.JavaDetailsQuery;

public interface JavaDetailsService {
    long insert(JavaDetails record);
    int deleteAll(JavaDetailsQuery query);
    int deleteByPrimaryId(Long PrimaryId);
    int updateByPrimaryId(JavaDetails record);
    int updateForPosts(JavaDetails record);
}
