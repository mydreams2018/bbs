package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.AssemblerPosts;
import cn.kungreat.bbs.mapper.AssemblerPostsMapper;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
import cn.kungreat.bbs.query.AssemblerPostsQuery;
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
@RequestMapping(value = "/assemblerPosts")
public class AssemblerPostsController {
    @Autowired
    private PostsCategoryService postsCategoryService;
    @Autowired
    private AssemblerPostsService assemblerPostsService;
    @Autowired
    private AssemblerPostsMapper assemblerPostsMapper;
    @Autowired
    private AssemblerDetailsService assemblerDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssemblerDetailsRecordService assemblerDetailsRecordService;

    @RequestMapping
    public String posts(Model model){
        model.addAttribute("categorys",postsCategoryService.selectAll(2));
        return "sendAssemblerPosts";
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(AssemblerPosts record){
        JsonResult jsonResult = new JsonResult();
        try {
            assemblerPostsService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/selectAll",method = RequestMethod.POST)
    public QueryResult selectAll(AssemblerPostsQuery query){
        return assemblerPostsService.selectAll(query);
    }

    @RequestMapping(value = "/details")
    public String details(@RequestParam Long id, Model model,Long currentPage){
        AssemblerPosts assemblerPosts = assemblerPostsMapper.selectByPrimaryKey(id);
        if(assemblerPosts != null){
            model.addAttribute("posts",assemblerPosts);
            AssemblerDetailsQuery assemblerDetailsQuery = new AssemblerDetailsQuery();
            assemblerDetailsQuery.setPostsId(id);
            assemblerDetailsQuery.setPageSize(10L);
            assemblerDetailsQuery.setCurrentPage(currentPage);
            QueryResult queryResult = assemblerDetailsService.selectByPostsId(assemblerDetailsQuery);
            model.addAttribute("details",queryResult);
            model.addAttribute("records",assemblerDetailsRecordService.selectByDetails(queryResult.getDatas()));
            model.addAttribute("postsUsers",userService.selectByAccounts(queryResult.getDatas()));
            return "assemblerDetails";
        }
        return "redirect:/assembler/assembler.html";
    }

    @ResponseBody
    @RequestMapping(value = "/updatePosts",method = RequestMethod.POST)
    public JsonResult updatePosts(AssemblerPosts record){
        JsonResult jsonResult = new JsonResult();
        try{
            assemblerPostsService.updateByPrimaryKey(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("/assemblerPosts/details?id="+record.getId());
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
