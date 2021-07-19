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
 * 订单主表
 * </p>
 *
 * @author Admin
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单号
     */
    private String sn;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 实际支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付方式  1:微信  2:支付宝 3:现金
     */
    private Boolean payType;

    /**
     * 支付状态  0:未支付 1：已支付 2：已退款
     */
    private Boolean payStatus;

    /**
     * 发货方式  0：自提  1：配送
     */
    private Boolean type;

    /**
     * 配送地址
     */
    private String address;

    /**
     * 发货状态  0:未发货 1：已发货
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否有效  0：有效  1：无效
     */
    private Boolean isDel;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;


}
