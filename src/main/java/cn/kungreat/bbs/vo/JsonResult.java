package cn.kungreat.bbs.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonResult {
    private boolean result = true;
    private String message;

    public JsonResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
    public JsonResult(){}
}