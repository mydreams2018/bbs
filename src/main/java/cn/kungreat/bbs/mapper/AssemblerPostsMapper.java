package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.AssemblerPosts;
import cn.kungreat.bbs.query.AssemblerPostsQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssemblerPostsMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(String account);

    long insert(AssemblerPosts record);

    AssemblerPosts selectByPrimaryKey(Long id);

    long selectCount(AssemblerPostsQuery query);
    List<AssemblerPosts> selectAll(AssemblerPostsQuery query);
    List<AssemblerPosts> selectByIds(@Param("ids") List<Long> ids);
    int updateByPrimaryKey(AssemblerPosts record);

    int updateByReply(AssemblerPosts record);
}