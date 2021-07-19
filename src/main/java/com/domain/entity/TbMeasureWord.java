package com.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 量词表
 * </p>
 *
 * @author Admin
 * @since 2020-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbMeasureWord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 单位名称
     */
    private String name;

    /**
     * 优先级 值越大 优先级越高
     */
    private Integer priority;

    /**
     * 是否有效 0：有效 1：无效
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
