package cn.kungreat.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/javaPosts")
public class JavaPostsController {

    @RequestMapping
    public String posts(){
        return "sendJavaPosts";
    }

}
