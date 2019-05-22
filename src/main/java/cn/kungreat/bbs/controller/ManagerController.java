package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.AssemblerPosts;
import cn.kungreat.bbs.domain.JavaPosts;
import cn.kungreat.bbs.mapper.AssemblerPostsMapper;
import cn.kungreat.bbs.mapper.JavaPostsMapper;
import cn.kungreat.bbs.query.AssemblerDetailsQuery;
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
    @Autowired
    private AssemblerPostsMapper assemblerPostsMapper;
    @Autowired
    private AssemblerDetailsService assemblerDetailsService;
    @Autowired
    private AssemblerDetailsRecordService assemblerDetailsRecordService;

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

    @PreAuthorize("hasRole('manager-assembler-show')")
    @RequestMapping(value = "/assembler")
    public String assembler(){
        return "/manager/assemblerPosts";
    }

    @PreAuthorize("hasRole('manager-assembler-edit')")
    @RequestMapping(value = "/editAssemblerDetails")
    public String assemblerDetails(@RequestParam Long id, Model model,Long currentPage){
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
            return "/manager/editAssemblerDetails";
        }
        return "redirect:/assembler/assembler.html";
    }

    @ResponseBody
    @PreAuthorize("hasRole('manager-assembler-delete')")
    @RequestMapping(value = "/deleteAssembler",method = RequestMethod.POST)
    public JsonResult deleteAssembler(@RequestParam Long id){
        JsonResult jsonResult = new JsonResult();
        try{
            assemblerDetailsService.deleteByPrimaryId(id);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setMessage(e.getMessage());
            jsonResult.setResult(false);
        }
        return jsonResult;
    }
}
