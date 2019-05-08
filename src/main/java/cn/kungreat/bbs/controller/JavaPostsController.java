package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.service.PostsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/javaPosts")
public class JavaPostsController {
    @Autowired
    private PostsCategoryService postsCategoryService;

    @RequestMapping
    public String posts(Model model){
        model.addAttribute("categorys",postsCategoryService.selectAll(1));
        return "sendJavaPosts";
    }

}
