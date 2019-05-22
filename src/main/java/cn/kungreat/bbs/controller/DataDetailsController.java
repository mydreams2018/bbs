package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.DataDetails;
import cn.kungreat.bbs.domain.DataPosts;
import cn.kungreat.bbs.query.DataDetailsQuery;
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
@RequestMapping(value = "/dataDetails")
public class DataDetailsController {
    @Autowired
    private DataDetailsService dataDetailsService;
    @Autowired
    private DataPostsService dataPostsService;
    @Autowired
    private PostsCategoryService postsCategoryService;

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(DataDetails record){
        JsonResult jsonResult = new JsonResult();
        try{
            dataDetailsService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam Long id, Model model){
        DataDetails dataDetails = dataDetailsService.selectByPrimaryId(id);
        model.addAttribute("detail", dataDetails);
        if(dataDetails.getIsPosts() == 1){
            DataPosts dataPosts = dataPostsService.selectByPrimaryKey(dataDetails.getPostsId());
            model.addAttribute("categorys",postsCategoryService.selectAll(1));
            model.addAttribute("posts",dataPosts);
            return "updateDataPosts";
        }
        return "updateDataDetails";
    }

    @ResponseBody
    @RequestMapping(value = "/updateSave",method = RequestMethod.POST)
    public JsonResult updateSave(DataDetails record){
        JsonResult jsonResult = new JsonResult();
        try{
            dataDetailsService.updateByPrimaryId(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("/dataPosts/details?id="+record.getPostsId());
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/selectReply",method = RequestMethod.POST)
    public QueryResult selectReply(DataDetailsQuery query) {
        return dataPostsService.selectReplyByAccount(query);
    }
}
