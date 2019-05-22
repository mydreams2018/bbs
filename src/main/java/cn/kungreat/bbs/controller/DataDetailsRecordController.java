package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.domain.DataDetailsRecord;
import cn.kungreat.bbs.service.DataDetailsRecordService;
import cn.kungreat.bbs.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/dataDetailsRecord")
public class DataDetailsRecordController {
    @Autowired
    private DataDetailsRecordService dataDetailsRecordService;

    @ResponseBody
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public JsonResult save(DataDetailsRecord record){
        JsonResult jsonResult = new  JsonResult();
        try {
            dataDetailsRecordService.insert(record);
            jsonResult.setMessage("success");
        }catch (Exception e){
            jsonResult.setResult(false);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }
}
