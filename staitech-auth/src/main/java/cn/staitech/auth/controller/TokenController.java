package cn.staitech.auth.controller;

import cn.staitech.auth.form.LoginBody;
import cn.staitech.auth.form.RegisterBody;
import cn.staitech.auth.form.out.LoginOut;
import cn.staitech.auth.form.out.PublicKeyGetOut;
import cn.staitech.auth.service.SysLoginService;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.utils.JwtUtils;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.security.auth.AuthUtil;
import cn.staitech.common.security.service.TokenService;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.model.LoginUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * token 控制
 *
 * @author staitech
 */
@RestController
@Api(value = "登录/登出", tags = "登录/登出")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @ApiOperation(value = "用户名称密码校验", notes = "用户名称密码校验-gjt")
    @PostMapping("userLoginVerify")
    public R<String> userLoginVerify(@RequestBody LoginBody form) {
        // 用户登录
        boolean res = sysLoginService.userLoginVerify(form.getUsername(), form.getPassword());

        if (res) {
            return R.ok("校验通过");
        } else {
            return R.fail("校验失败");
        }
    }

    @ApiOperation(value = "登录2.0", notes = "登录-wudi")
    @PostMapping("/login")
    public R<LoginOut> login(@RequestBody LoginBody form) {
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        userInfo.setLanguage(form.getLanguage());
        // 获取登录token
        LoginOut resp = new LoginOut();
        Map<String, Object> token = tokenService.createToken(userInfo);
        resp.setAccessToken((String) token.get("access_token"));
        resp.setExpiresIn((Long) token.get("expires_in"));
        resp.setLoginState(userInfo.getSysUser().getLoginStatus());
        return R.ok(resp);
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {

        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request) {

        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser, false);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody) {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }

    @ApiOperation(value = "获得加密公钥", response = PublicKeyGetOut.class, notes = "获得加密公钥-wudi")
    @GetMapping("getPublicKey")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String", paramType = "query")})
    public R<PublicKeyGetOut> getPublicKey(@RequestParam("userName") String userName) {

        String pattern = "^[a-zA-Z0-9]+$";
        if(userName.matches(pattern)){
            return R.ok(sysLoginService.getPublicKey(userName));
        }else{
            return R.fail("USERNAME_ERROR_FORMART");
        }


    }

    @ApiOperation(value = "首页-双语切换")
    @GetMapping("/languageSwitching")
    public R languageSwitching(
            @NotNull(message = "{TokenController.languageSwitching.isnull}") @RequestParam(value = "language") @ApiParam(name = "language", value = "语言类型：英文en-us；中文zh-cn", required = true) String language
    ) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        loginUser.setLanguage(language);
        if (StringUtils.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser, false);
            return R.ok();
        }
        return R.ok();
    }
}
