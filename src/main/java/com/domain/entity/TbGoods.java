package com.domain.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author Admin
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String img;

    /**
     * 商品类别ID
     */
    private Long classId;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 商品条码
     */
    private String barCode;

    /**
     * 商品计量单位(如：件，瓶)
     */
    private String measure;

    /**
     * 商品库存
     */
    private Integer inventory;

    /**
     * 商品详情说明
     */
    private String detail;

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
