package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.JavaDetails;
import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.service.JavaDetailsService;
import cn.kungreat.bbs.service.JavaPostsService;
import cn.kungreat.bbs.service.PostsCategoryService;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/javaDetails")
public class JavaDetailsController {
    @Autowired
    private JavaDetailsService javaDetailsService;
    @Autowired
    private JavaPostsService javaPostsService;
    @Autowired
    private PostsCategoryService postsCategoryService;

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(JavaDetails record){
        JsonResult jsonResult = new JsonResult();
        try{
            javaDetailsService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam Long id, Model model){
        JavaDetails javaDetails = javaDetailsService.selectByPrimaryId(id);
        model.addAttribute("detail", javaDetails);
        if(javaDetails.getIsPosts() == 1){
            JavaPosts javaPosts = javaPostsService.selectByPrimaryKey(javaDetails.getPostsId());
            model.addAttribute("categorys",postsCategoryService.selectAll(1));
            model.addAttribute("posts",javaPosts);
            return "updateJavaPosts";
        }
        return "updateJavaDetails";
    }

    @ResponseBody
    @RequestMapping(value = "/updateSave",method = RequestMethod.POST)
    public JsonResult updateSave(JavaDetails record){
        JsonResult jsonResult = new JsonResult();
        try{
            javaDetailsService.updateByPrimaryId(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("/javaPosts/javaDetails?id="+record.getPostsId());
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
