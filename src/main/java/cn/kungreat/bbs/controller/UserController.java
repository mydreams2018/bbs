package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.Permission;
import cn.kungreat.bbs.query.UserQuery;
import cn.kungreat.bbs.service.PermissionMappingService;
import cn.kungreat.bbs.service.PermissionService;
import cn.kungreat.bbs.service.UserService;
import cn.kungreat.bbs.vo.JsonResult;
import cn.kungreat.bbs.vo.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private PermissionMappingService permissionMappingService;

    @RequestMapping(value = "/listAndCensus")
    public String listAndCensus(UserQuery query,Model model){
        model.addAttribute("categoryTotal",userService.selectCategoryTotal(query));
        return "userCensus";
    }

    @ResponseBody
    @RequestMapping(value = "/categoryNames")
    public List<String> categoryNames(){
        return userService.categoryNames();
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public QueryResult  list(UserQuery query){
        return userService.query(query);
    }

    @RequestMapping(value = "/permissions")
    public String permissions(String account,Model model){
        model.addAttribute("account",account);
        List<String> strings = permissionService.selectPermissions(account);
        int pSize = strings==null?0:strings.size();
        List<Permission> permissions = permissionService.selectAll();
        int aSize = permissions==null?0:permissions.size();
        model.addAttribute("PS", strings);
        model.addAttribute("PSize", pSize);
        model.addAttribute("allPS", permissions);
        model.addAttribute("aSize", aSize);
        return "userPermissions";
    }

    @ResponseBody
    @RequestMapping(value = "/savePermissions",method = RequestMethod.POST)
    public JsonResult savePermissions(String account,String listPermission){
        JsonResult jsonResult = new JsonResult();
        try {
            String[] st = listPermission.trim().split(",");
            permissionMappingService.insertBatch(Arrays.asList(st),account);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setMessage(e.getMessage());
            jsonResult.setResult(false);
        }
        return jsonResult;
    }
}
