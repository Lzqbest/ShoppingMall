package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author Admin
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
