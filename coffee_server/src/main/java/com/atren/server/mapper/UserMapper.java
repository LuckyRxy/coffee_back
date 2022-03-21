package com.atren.server.mapper;

import com.atren.server.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 找到数据库中具有此用户名的数量
     * @param username
     * @return
     */
    Integer findUserName(String username);
}
