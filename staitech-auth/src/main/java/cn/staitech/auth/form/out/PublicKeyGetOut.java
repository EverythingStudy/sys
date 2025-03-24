package cn.staitech.auth.form.out;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PublicKeyGetOut {
    @ApiModelProperty(value = "公钥")
    private String publicKey;
}
