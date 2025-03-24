package cn.staitech.system.domain.user.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gjt.
 * @data 2023/6/2 15:18
 */
@Data
public class UserSelectBy {

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

}
