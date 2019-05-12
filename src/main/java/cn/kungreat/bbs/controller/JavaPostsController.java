package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.mapper.JavaPostsMapper;
import cn.kungreat.bbs.query.JavaDetailsQuery;
import cn.kungreat.bbs.query.JavaPostsQuery;
import cn.kungreat.bbs.service.JavaDetailsService;
import cn.kungreat.bbs.service.JavaPostsService;
import cn.kungreat.bbs.service.PostsCategoryService;
import cn.kungreat.bbs.service.UserService;
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
@RequestMapping(value = "/javaPosts")
public class JavaPostsController {
    @Autowired
    private PostsCategoryService postsCategoryService;
    @Autowired
    private JavaPostsService javaPostsService;
    @Autowired
    private JavaPostsMapper javaPostsMapper;
    @Autowired
    private JavaDetailsService javaDetailsService;
    @Autowired
    private UserService userService;

    @RequestMapping
    public String posts(Model model){
        model.addAttribute("categorys",postsCategoryService.selectAll(1));
        return "sendJavaPosts";
    }

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(JavaPosts record){
        JsonResult jsonResult = new JsonResult();
        try {
            javaPostsService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/selectAll",method = RequestMethod.POST)
    public QueryResult selectAll(JavaPostsQuery query){
        return javaPostsService.selectAll(query);
    }

    @RequestMapping(value = "/javaDetails")
    public String javaDetails(@RequestParam Long id, Model model,Long currentPage){
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(id);
        if(javaPosts != null){
            model.addAttribute("posts",javaPosts);
            JavaDetailsQuery javaDetailsQuery = new JavaDetailsQuery();
            javaDetailsQuery.setPostsId(id);
            javaDetailsQuery.setPageSize(10L);
            javaDetailsQuery.setCurrentPage(currentPage);
            QueryResult queryResult = javaDetailsService.selectByPostsId(javaDetailsQuery);
            model.addAttribute("details",queryResult);
            model.addAttribute("postsUsers",userService.selectByAccounts(queryResult.getDatas()));
            return "javaDetails";
        }
        return "redirect:/java.html";
    }

    @ResponseBody
    @RequestMapping(value = "/updatePosts",method = RequestMethod.POST)
    public JsonResult updatePosts(JavaPosts record){
        JsonResult jsonResult = new JsonResult();
        try{
            javaPostsService.updateByPrimaryKey(record);
            jsonResult.setMessage("success");
            jsonResult.setPath("/javaPosts/javaDetails?id="+record.getId());
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
