package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.query.JavaPostsQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JavaPostsMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(String account);

    long insert(JavaPosts record);

    JavaPosts selectByPrimaryKey(Long id);

    long selectCount(JavaPostsQuery query);
    List<JavaPosts> selectAll(JavaPostsQuery query);
    List<JavaPosts> selectByIds(@Param("ids") List<Long> ids);
    int updateByPrimaryKey(JavaPosts record);

    int updateByReply(JavaPosts record);
}