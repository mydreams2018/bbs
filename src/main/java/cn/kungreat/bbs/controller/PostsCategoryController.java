package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.PostsCategory;
import cn.kungreat.bbs.service.PostsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/postsCategory")
public class PostsCategoryController {
    @Autowired
    private PostsCategoryService postsCategoryService;

    @ResponseBody
    @RequestMapping(value = "/list")
    List<PostsCategory> selectAll(@RequestParam Integer state){
        return postsCategoryService.selectAll(state);
    }
}
