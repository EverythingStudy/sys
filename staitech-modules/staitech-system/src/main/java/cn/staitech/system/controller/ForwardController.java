package cn.staitech.system.controller;

import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.RemoteLabelService;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author wudi
 * @Date 2023/9/14 11:22
 * @desc
 */
@Slf4j
@Api(value = "phyon接口转发",tags="phyon接口转发")
@RestController
@RequestMapping("/forward")
public class ForwardController {
    @Autowired
    private RemoteLabelService remoteLabelService;

    @ApiOperation("点标注")
    @PostMapping("/label")
    public JSONObject getLabel(@RequestBody JSONObject req) throws Exception {
        log.info("标注点接口开始：");
        req.put("user",SecurityUtils.getUsername());
        try {
            JSONObject s = remoteLabelService.getLabel(req);
            return s;
        } catch (Exception e) {
            log.info(e.toString());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",500);
            jsonObject.put("msg",e.toString());
            return jsonObject;
        }
    }
}
