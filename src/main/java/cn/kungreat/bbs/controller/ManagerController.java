package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.mapper.JavaPostsMapper;
import cn.kungreat.bbs.query.JavaDetailsQuery;
import cn.kungreat.bbs.service.*;
import cn.kungreat.bbs.vo.JsonResult;
import cn.kungreat.bbs.vo.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

    @Autowired
    private JavaPostsMapper javaPostsMapper;
    @Autowired
    private JavaDetailsService javaDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaDetailsRecordService javaDetailsRecordService;

    @PreAuthorize("hasRole('manager-java-show')")
    @RequestMapping(value = "/java")
    public String java(){
        return "/manager/javaPosts";
    }

    @PreAuthorize("hasRole('manager-java-edit')")
    @RequestMapping(value = "/editJavaDetails")
    public String javaDetails(@RequestParam Long id, Model model, Long currentPage){
        JavaPosts javaPosts = javaPostsMapper.selectByPrimaryKey(id);
        if(javaPosts != null){
            model.addAttribute("posts",javaPosts);
            JavaDetailsQuery javaDetailsQuery = new JavaDetailsQuery();
            javaDetailsQuery.setPostsId(id);
            javaDetailsQuery.setPageSize(10L);
            javaDetailsQuery.setCurrentPage(currentPage);
            QueryResult queryResult = javaDetailsService.selectByPostsId(javaDetailsQuery);
            model.addAttribute("details",queryResult);
            model.addAttribute("records",javaDetailsRecordService.selectByDetails(queryResult.getDatas()));
            model.addAttribute("postsUsers",userService.selectByAccounts(queryResult.getDatas()));
            return "/manager/editJavaDetails";
        }
        return "redirect:/java.html";
    }

    @ResponseBody
    @PreAuthorize("hasRole('manager-java-delete')")
    @RequestMapping(value = "/deleteJava",method = RequestMethod.POST)
    public JsonResult deleteJava(@RequestParam Long id){
        JsonResult jsonResult = new JsonResult();
        try{
            javaDetailsService.deleteByPrimaryId(id);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setMessage(e.getMessage());
            jsonResult.setResult(false);
        }
        return jsonResult;
    }
}
