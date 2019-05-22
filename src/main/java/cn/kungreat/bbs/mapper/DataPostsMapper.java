package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.DataPosts;
import cn.kungreat.bbs.query.DataPostsQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataPostsMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByAccount(String account);

    long insert(DataPosts record);

    DataPosts selectByPrimaryKey(Long id);

    long selectCount(DataPostsQuery query);
    List<DataPosts> selectAll(DataPostsQuery query);
    List<DataPosts> selectByIds(@Param("ids") List<Long> ids);
    int updateByPrimaryKey(DataPosts record);

    int updateByReply(DataPosts record);
}