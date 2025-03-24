package cn.staitech.system.controller;

import cn.staitech.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王峰
 * @date 2022/11/1 14:10
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/api")
    public String sendMsg() {
        SecurityUtils.getLoginUser().getLanguage();
        return "测试Jenkins构建是否更新";
    }

}
