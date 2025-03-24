package cn.staitech.system.controller;

import cn.staitech.common.core.constant.UserConstants;
import cn.staitech.common.core.utils.poi.ExcelUtil;
import cn.staitech.common.core.web.controller.BaseController;
import cn.staitech.common.core.web.domain.AjaxResult;
import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.common.log.annotation.Log;
import cn.staitech.common.log.enums.BusinessType;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.domain.SysPost;
import cn.staitech.system.service.ISysPostService;
import cn.staitech.system.utils.MessageSource;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author staitech
 */
@Api(value = "岗位信息操作处理", tags = "岗位信息操作处理")
@RestController
@RequestMapping("/post")
public class SysPostController extends BaseController {
    @Autowired
    private ISysPostService postService;

    /**
     * 获取岗位列表
     */
    @RequiresPermissions("system:post:list")
    @GetMapping("/list")
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPost post) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, MessageSource.M("POST_DATA"));
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @RequiresPermissions("system:post:query")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId) {
        return AjaxResult.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error(MessageSource.M("ADD_POST").concat(post.getPostName()).concat(MessageSource.M("ADD_UPDATE_POST_ERROR")));
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error(MessageSource.M("ADD_POST").concat(post.getPostName()).concat(MessageSource.M("ADD_UPDATE_POST_ERROR_HAD_CODE")));
        }
        post.setCreateBy(SecurityUtils.getUserId());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error(MessageSource.M("UPDATE_POST").concat(post.getPostName()).concat(MessageSource.M("ADD_UPDATE_POST_ERROR")));
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error(MessageSource.M("UPDATE_POST").concat(post.getPostName()).concat(MessageSource.M("ADD_UPDATE_POST_ERROR_HAD_CODE")));
        }
        post.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @RequiresPermissions("system:post:remove")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        List<SysPost> posts = postService.selectPostAll();
        return AjaxResult.success(posts);
    }
}
