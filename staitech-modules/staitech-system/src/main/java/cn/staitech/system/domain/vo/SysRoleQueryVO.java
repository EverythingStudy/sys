package cn.staitech.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project ：be.PathMedics.SaaS.java.system
 * @File ：SysRoleVO
 * @Author ：yanglei
 * @Email ：yangl@staitech.cn
 * @Date ：2023/5/28 星期日 20:23
 * @Description ：
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", hidden = true)
    private Long roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /**
     * 角色状态（0正常 1停用）
     */
    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间 ")
    private Map<String, Object> createTimeParams;

    @ApiModelProperty(value = "当前页数")
    private int pageNum;

    @ApiModelProperty(value = "每页数据条数")
    private int pageSize;

    public Map<String, Object> getCreateTimeParams() {
        if (createTimeParams == null) {
            createTimeParams = new HashMap<>();
        }
        return createTimeParams;
    }
}
