package cn.kungreat.bbs.service;

import cn.kungreat.bbs.domain.PostsCategory;

import java.util.List;

public interface PostsCategoryService {

    int insert(PostsCategory record);

    List<PostsCategory> selectAll(Integer state);

}
