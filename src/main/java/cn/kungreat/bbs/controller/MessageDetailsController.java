package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.MessageDetails;
import cn.kungreat.bbs.query.MessageDetailsQuery;
import cn.kungreat.bbs.service.MessageDetailsService;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/messageDetails")
public class MessageDetailsController {
    @Autowired
    private MessageDetailsService messageDetailsService;

    @RequestMapping(value = "/list")
    public String list(MessageDetailsQuery query, Model model){
        try{
            model.addAttribute("MSID",query.getMessageId());
            model.addAttribute("details",messageDetailsService.list(query));
        }catch (Exception e){
            return "redirect:/home.html";
        }
        return "messageDetails";
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JsonResult jsonResult(Long id){
        JsonResult jsonResult = new JsonResult();
        try {
            messageDetailsService.deleteByPrimaryKey(id);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setMessage(e.getMessage());
            jsonResult.setResult(false);
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(MessageDetails record){
        JsonResult jsonResult = new JsonResult();
        try {
            messageDetailsService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
