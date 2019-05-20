package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.AssemblerDetailsRecord;
import cn.kungreat.bbs.service.AssemblerDetailsRecordService;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/assemblerDetailsRecord")
public class AssemblerDetailsRecordController {
    @Autowired
    private AssemblerDetailsRecordService assemblerDetailsRecordService;

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(AssemblerDetailsRecord record){
        JsonResult jsonResult = new  JsonResult();
        try {
            assemblerDetailsRecordService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
