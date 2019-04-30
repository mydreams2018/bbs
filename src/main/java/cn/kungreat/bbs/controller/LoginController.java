package cn.kungreat.bbs.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping(value = "/index")
    public String index(){
      /*  String isAuth = SecurityContextHolder.getContext().getAuthentication().getName();
        if(isAuth != null && !"anonymousUser".equals(isAuth)){
            return "redirect:/hello";
        }*/
        return "login";
    }

}