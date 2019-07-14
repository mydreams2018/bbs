package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.service.UserService;
import cn.kungreat.bbs.util.UserContext;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public String index(){
        if(UserContext.getCurrentName() != null && !"".equals(UserContext.getCurrentName())){
            return "redirect:/home.html";
        }
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/register")
    public JsonResult register(User record){
        JsonResult jsonResult = new JsonResult();
        try{
            userService.insert(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("index");
        }catch (Exception e){
            e.getMessage();
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

}