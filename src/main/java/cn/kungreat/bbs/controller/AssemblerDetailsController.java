package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.AssemblerDetails;
import cn.kungreat.bbs.domain.AssemblerPosts;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
import cn.kungreat.bbs.service.*;
import cn.kungreat.bbs.vo.JsonResult;
import cn.kungreat.bbs.vo.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/assemblerDetails")
public class AssemblerDetailsController {
    @Autowired
    private AssemblerDetailsService assemblerDetailsService;
    @Autowired
    private AssemblerPostsService assemblerPostsService;
    @Autowired
    private PostsCategoryService postsCategoryService;

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(AssemblerDetails record){
        JsonResult jsonResult = new JsonResult();
        try{
            assemblerDetailsService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam Long id, Model model){
        AssemblerDetails assemblerDetails = assemblerDetailsService.selectByPrimaryId(id);
        model.addAttribute("detail", assemblerDetails);
        if(assemblerDetails.getIsPosts() == 1){
            AssemblerPosts assemblerPosts = assemblerPostsService.selectByPrimaryKey(assemblerDetails.getPostsId());
            model.addAttribute("categorys",postsCategoryService.selectAll(2));
            model.addAttribute("posts",assemblerPosts);
            return "updateAssemblerPosts";
        }
        return "updateAssemblerDetails";
    }

    @ResponseBody
    @RequestMapping(value = "/updateSave",method = RequestMethod.POST)
    public JsonResult updateSave(AssemblerDetails record){
        JsonResult jsonResult = new JsonResult();
        try{
            assemblerDetailsService.updateByPrimaryId(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("/assemblerPosts/details?id="+record.getPostsId());
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/selectReply",method = RequestMethod.POST)
    public QueryResult selectReply(AssemblerDetailsQuery query) {
        return assemblerPostsService.selectReplyByAccount(query);
    }
}
