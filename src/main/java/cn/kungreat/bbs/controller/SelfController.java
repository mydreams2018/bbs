package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.User;
import cn.kungreat.bbs.service.UserService;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Controller
@RequestMapping(value = "/self")
public class SelfController {
    @Autowired
    private UserService userService;
    @Value("${user.imgPath}")
    private String path;

    @RequestMapping(value = "/list")
    public String list(Model model){
        model.addAttribute("userDetail",
                userService.selectByPrimaryKey(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "self";
    }

    @ResponseBody
    @RequestMapping(value = "/uploadImg")
    public JsonResult jsonResult(MultipartFile imgPath){
        JsonResult jsonResult = new JsonResult();
        try{
            Assert.isTrue(imgPath.getSize() < 1048576,"上传文件不能大于1M");
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Assert.isTrue("image/jpeg".equals(imgPath.getContentType())
                    ||"image/gif".equals(imgPath.getContentType()),"只支持jpg或gif格式的图片");
            String type = imgPath.getContentType().split("/")[1];
            String img = path +"userImg/"+ name+"."+ type;
            imgPath.transferTo(new File(img));
            String path = "/userImg/"+name+"."+ type;
            userService.updateImg(name,path);
            jsonResult.setPath(path);
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/changeData")
    public JsonResult changeData(User user){
        JsonResult jsonResult = new JsonResult();
        try{
            user.setAccount(SecurityContextHolder.getContext().getAuthentication().getName());
            userService.updateByPrimaryKey(user);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
