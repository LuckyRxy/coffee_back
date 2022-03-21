package com.atren.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录pojo
 *
 * @author rxyLucky
 * @create 2022-02-28 9:44
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "UserLoginParam",description = "")
public class UserLoginParam {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String captcha;
}
