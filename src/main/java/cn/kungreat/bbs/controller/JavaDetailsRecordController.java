package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.JavaDetailsRecord;
import cn.kungreat.bbs.service.JavaDetailsRecordService;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/javaDetailsRecord")
public class JavaDetailsRecordController {
    @Autowired
    private JavaDetailsRecordService javaDetailsRecordService;

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(JavaDetailsRecord record){
        JsonResult jsonResult = new  JsonResult();
        try {
            javaDetailsRecordService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
