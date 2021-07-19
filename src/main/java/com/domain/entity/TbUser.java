package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Admin
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String img;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 微信openid
     */
    @TableField("openId")
    private String openId;

    /**
     * 配送地址
     */
    private String address;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 是否有效  0：有效  1：无效
     */
    private Boolean isDel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;


}
