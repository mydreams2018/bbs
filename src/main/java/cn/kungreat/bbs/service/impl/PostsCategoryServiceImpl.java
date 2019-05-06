package cn.kungreat.bbs.service.impl;

import cn.kungreat.bbs.domain.PostsCategory;
import cn.kungreat.bbs.mapper.PostsCategoryMapper;
import cn.kungreat.bbs.service.PostsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostsCategoryServiceImpl implements PostsCategoryService {
    @Autowired
    private PostsCategoryMapper postsCategoryMapper;


    @Override
    public int insert(PostsCategory record) {
        return postsCategoryMapper.insert(record);
    }

    @Override
    public List<PostsCategory> selectAll(Integer state) {
        return postsCategoryMapper.selectAll(state);
    }

}