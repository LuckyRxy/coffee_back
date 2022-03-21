package com.atren.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录实体类
 * @author rxyLucky
 * @create 2022-02-10 13:07
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "AdminLoginParam",description = "")
public class AdminLoginParam {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String captcha;
}
