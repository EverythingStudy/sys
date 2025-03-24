package cn.staitech.system.domain.vo;

import cn.staitech.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于查询用户列表，只返回用户ID和用户名
 * @author wangf
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserIdVo {

    @ApiModelProperty(value = "用户ID")
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    @ApiModelProperty(value = "登录名称、用户名")
    private String userName;
}
