package cn.kungreat.bbs.mapper;

import cn.kungreat.bbs.domain.PostsCategory;
import java.util.List;

public interface PostsCategoryMapper {
    int insert(PostsCategory record);

    List<PostsCategory> selectAll(Integer state);
}