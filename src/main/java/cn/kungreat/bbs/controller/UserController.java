package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.query.UserQuery;
import cn.kungreat.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/listAndCensus")
    public String listAndCensus(UserQuery query,Model model){
        model.addAttribute("categoryTotal",userService.selectCategoryTotal(query));
        return "userCensus";
    }

    @ResponseBody
    @RequestMapping(value = "/categoryNames")
    public List<String> categoryNames(){
        return userService.categoryNames();
    }

}
