package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.DataPosts;
import cn.kungreat.bbs.mapper.DataPostsMapper;
import cn.kungreat.bbs.query.DataDetailsQuery;
import cn.kungreat.bbs.query.DataPostsQuery;
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
@RequestMapping(value = "/dataPosts")
public class DataPostsController {
    @Autowired
    private PostsCategoryService postsCategoryService;
    @Autowired
    private DataPostsService dataPostsService;
    @Autowired
    private DataPostsMapper dataPostsMapper;
    @Autowired
    private DataDetailsService dataDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private DataDetailsRecordService dataDetailsRecordService;

    @RequestMapping
    public String posts(Model model){
        model.addAttribute("categorys",postsCategoryService.selectAll(3));
        return "sendDataPosts";
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(DataPosts record){
        JsonResult jsonResult = new JsonResult();
        try {
            dataPostsService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/selectAll",method = RequestMethod.POST)
    public QueryResult selectAll(DataPostsQuery query){
        return dataPostsService.selectAll(query);
    }

    @RequestMapping(value = "/details")
    public String javaDetails(@RequestParam Long id, Model model,Long currentPage){
        DataPosts dataPosts = dataPostsMapper.selectByPrimaryKey(id);
        if(dataPosts != null){
            model.addAttribute("posts",dataPosts);
            DataDetailsQuery dataDetailsQuery = new DataDetailsQuery();
            dataDetailsQuery.setPostsId(id);
            dataDetailsQuery.setPageSize(10L);
            dataDetailsQuery.setCurrentPage(currentPage);
            QueryResult queryResult = dataDetailsService.selectByPostsId(dataDetailsQuery);
            model.addAttribute("details",queryResult);
            model.addAttribute("records",dataDetailsRecordService.selectByDetails(queryResult.getDatas()));
            model.addAttribute("postsUsers",userService.selectByAccounts(queryResult.getDatas()));
            return "dataDetails";
        }
        return "redirect:/data/data.html";
    }

    @ResponseBody
    @RequestMapping(value = "/updatePosts",method = RequestMethod.POST)
    public JsonResult updatePosts(DataPosts record){
        JsonResult jsonResult = new JsonResult();
        try{
            dataPostsService.updateByPrimaryKey(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("/dataPosts/details?id="+record.getId());
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
