package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.SendMessage;
import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.query.SendMessageQuery;
import cn.kungreat.bbs.service.SendMessageService;
import cn.kungreat.bbs.service.UserService;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/sendMessage")
public class SendMessageController {
    @Autowired
    private SendMessageService sendMessageService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/from")
    public String from(SendMessageQuery query, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        query.setPageSize(3L);
        query.setFromAccount(name);
        model.addAttribute("ms",sendMessageService.list(query));
        return "/fromMessage";
    }

    @RequestMapping(value = "/to")
    public String to(SendMessageQuery query, Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        query.setPageSize(3L);
        query.setToAccount(name);
        model.addAttribute("ms",sendMessageService.list(query));
        return "/toMessage";
    }

    @RequestMapping(value = "/send")
    public String send(String toAccount,Model model){
        User user = userService.selectByPrimaryKey(toAccount);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if(user == null || name.equals(user.getAccount())){
            return "redirect:/home.html";
        }
        model.addAttribute("toUser",user);
        return "sendMessage";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult save(SendMessage record){
        JsonResult jsonResult = new JsonResult();
        try{
            sendMessageService.insert(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("/sendMessage/from");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
