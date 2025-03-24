package cn.staitech.system.controller;

import cn.staitech.common.core.constant.UserConstants;
import cn.staitech.common.core.domain.R;
import cn.staitech.common.core.utils.StringUtils;
import cn.staitech.common.core.utils.file.FileTypeUtils;
import cn.staitech.common.core.utils.file.MimeTypeUtils;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.domain.AjaxResult;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.security.service.TokenService;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.RemoteFileService;
import cn.staitech.system.api.domain.SysFile;
import cn.staitech.system.api.domain.SysUser;
import cn.staitech.system.api.model.LoginUser;
import cn.staitech.system.service.ISysUserService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 个人信息 业务处理
 *
 * @author staitech
 */

@Api(value = "个人信息 业务处理", tags = "个人信息 业务处理")
@RestController
@RequestMapping("/user/profile")
public class SysProfileController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(username));
        ajax.put("postGroup", userService.selectUserPostGroup(username));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        user.setUserName(sysUser.getUserName());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error(MessageSource.M("UPDATE_USER").concat("'").concat(user.getUserName()).concat("'").concat(MessageSource.M("ADD_USER_ERROR_PHONE_HAD")));
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error(MessageSource.M("UPDATE_USER").concat("'").concat(user.getUserName()).concat("'").concat(MessageSource.M("ADD_UPDATE_USER_ERROR_HAD_EMAIL")));
        }
        user.setUserId(sysUser.getUserId());
        user.setPassword(null);
        if (userService.updateUserProfile(user) > 0) {
            // 更新缓存用户信息
            loginUser.getSysUser().setNickName(user.getNickName());
            loginUser.getSysUser().setPhonenumber(user.getPhonenumber());
            loginUser.getSysUser().setEmail(user.getEmail());
            loginUser.getSysUser().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success(MessageSource.M("OPERATE_SUCCEED"));
        }
        return AjaxResult.error(MessageSource.M("ADD_UPDATE_USER_ERROR"));
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        String username = SecurityUtils.getUsername();
        SysUser user = userService.selectUserByUserName(username);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error(MessageSource.M("ADD_UPDATE_PASSWORD_ERROR_OLD"));
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error(MessageSource.M("ADD_UPDATE_PASSWORD_ERROR_SAME"));
        }
        if (userService.resetUserPwd(username, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            LoginUser loginUser = SecurityUtils.getLoginUser();
            loginUser.getSysUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success(MessageSource.M("OPERATE_SUCCEED"));
        }
        return AjaxResult.error(MessageSource.M("ADD_UPDATE_PASSWORD_ERROR"));
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) {
        if (!file.isEmpty()) {
            LoginUser loginUser = SecurityUtils.getLoginUser();
            String extension = FileTypeUtils.getExtension(file);
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return AjaxResult.error(MessageSource.M("FILE_FORMAT_ERROR").concat(Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION)).concat(MessageSource.M("FILE_FORMAT")));
            }
            R<SysFile> fileResult = remoteFileService.upload(file);
            if (StringUtils.isNull(fileResult) || StringUtils.isNull(fileResult.getData())) {
                return AjaxResult.error(MessageSource.M("FILE_SERVICE_BAD"));
            }
            String url = fileResult.getData().getUrl();
            if (userService.updateUserAvatar(loginUser.getUsername(), url)) {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", url);
                // 更新缓存用户头像
                // loginUser.getSysUser().setAvatar(url);
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error(MessageSource.M("FILE_SERVICE_ERROR"));
    }
}
