package cn.staitech.system.controller;


import cn.staitech.common.core.domain.R;
import cn.staitech.common.security.annotation.RequiresPermissions;
import cn.staitech.system.domain.vo.organization.*;
import cn.staitech.system.service.SysOrganizationService;
import cn.staitech.system.utils.MessageSource;
import cn.staitech.system.utils.PageMaster;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

import static cn.staitech.common.core.utils.SysRoleUtil.getOrganizationCode;

/**
 * @author gjt
 */
@Slf4j
@Api(value = "机构管理", tags = "机构管理")
@RestController
@RequestMapping("/organization")
public class SysOrganizationController {

    @Resource
    private SysOrganizationService organizationService;

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "根据机构id查询详情信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "organizationId", value = "机构id", required = true, dataType = "Long", paramType = "query")})
    @GetMapping("/selectBy")
    public R<OrganizationSelectResVo> selectBy(Long organizationId) {
        return R.ok(organizationService.selectPrimaryKey(organizationId));

    }

    /**
     * 根据主键查询详情信息
     *
     * @param req 查询的参数
     * @return List
     */
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "当前记录起始索引", dataTypeClass = Integer.class, paramType = "query", example = "1"), @ApiImplicitParam(name = "pageSize", value = "每页显示记录数", dataTypeClass = Integer.class, paramType = "query", example = "10")})
    @RequiresPermissions("system:organization:query")
    @ApiOperation(value = "查询机构信息")
    @PostMapping("/selectList")
    public R<PageMaster<OrganizationSelectResVo>> selectList(@Validated @RequestBody OrganizationSelectVo req) {
        PageHelper.startPage(req.getPageNum(), req.getPageSize()).setReasonable(true);
        List<OrganizationSelectResVo> organizationList = organizationService.selectList(req);
        if (!CollectionUtils.isEmpty(organizationList)) {
            organizationList.forEach(e -> {
                e.setOrganizationCode(getOrganizationCode(e.getOrganizationNumber()));
            });

        }
        PageMaster<OrganizationSelectResVo> pageMaster = new PageMaster<>(organizationList);
        return R.ok(pageMaster);
    }

    @ApiOperation(value = "添加机构信息")
    @RequiresPermissions("system:organization:add")
    @PostMapping("/insert")
    public R<String> insert(@Validated @RequestBody OrganizationInsertVo req) {
        organizationService.insert(req);
        return R.ok(null, MessageSource.M("OPERATE_SUCCEED"));
    }

    /**
     * 删除机构信息
     *
     * @param organizationId 机构id
     * @return true||False
     * 逻辑删除,更新机构表中del_flag状态
     */
    @ApiOperation(value = "删除机构信息")
    @RequiresPermissions("system:organization:remove")
    @PostMapping("/delete")
    @ApiImplicitParams({@ApiImplicitParam(name = "organizationId", value = "机构id", required = true, dataType = "Long", paramType = "query")})
    public R<String> delete(@RequestParam("organizationId") Long organizationId) throws ParseException {
        organizationService.delete(organizationId);
        return R.ok(null, MessageSource.M("OPERATE_SUCCEED"));
    }

    @ApiOperation(value = "修改机构信息")
    @RequiresPermissions("system:organization:edit")
    @PostMapping("/update")
    public R<String> update(@Validated @RequestBody OrganizationUpdateVo req) {
        organizationService.update(req);
        return R.ok(null, MessageSource.M("OPERATE_SUCCEED"));
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "修改机构信息")
    @RequiresPermissions("system:organization:status")
    @PostMapping("/updateStatus")
    public R<String> updateStatus(@Validated @RequestBody OrganizationUpdateStatusVo req) {
        organizationService.updateStatus(req);
        return R.ok(null, MessageSource.M("OPERATE_SUCCEED"));
    }

}
