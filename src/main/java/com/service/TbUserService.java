package com.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domain.entity.TbUser;
import com.domain.query.UserQuery;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Admin
 * @since 2020-12-11
 */
public interface TbUserService {

    /**
     * 添加用户
     * @return
     */
    boolean addUser();

    /**
     * 根据id查询用户姓名和电话号码
     * @return
     */
    TbUser getUserById();

    /**
     * 分页查询用户信息
     * @return
     */
    Page<TbUser> page(UserQuery query);

    /**
     * 修改用户
     * @param tbUser
     * @return
     */
    boolean update(TbUser tbUser);

    /**
     * 查询所有用户
     * @return
     */
    List<TbUser> queryAllUser();

    /**
     * 添加用户
     * @return
     */
    boolean insertUser();
}
