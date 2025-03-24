package cn.staitech.system.controller;

import cn.staitech.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

/**
 * @author: wangfeng
 * @create: 2023-08-21 16:01:37
 * @Description: 服务器系统时间
 */
@Api(value = "服务器系统时间", tags = "服务器系统时间")
@RestController
@RequestMapping("/time")
public class SysTimeController {

    /**
     * 服务器系统时间
     */
    @ApiOperation(value = "服务器系统时间")
    @GetMapping("/now")
    public R<Date> now() {
        return R.ok(new Date());
    }

    @ApiOperation(value = "服务器系统时间戳")
    @GetMapping("/timeStamp")
    public R<Long> timeStamp() {
        return R.ok(System.currentTimeMillis());
    }
}
