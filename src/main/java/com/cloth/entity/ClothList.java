package com.cloth.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ClothList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cloth_id", type = IdType.AUTO)
    private Integer clothId;

    private String clothType;

    private BigDecimal price;

    private String status;

    private String detail;

    private Integer userlistId;

    private String userlistName;


}
