package com.cloth.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaidVO {

    private Integer id;

    private String clothDetail;

    private BigDecimal price;

    private Date date;

    private Date paydate;

    private String name;

    private Integer userlistId;

    private String status;

    private Integer clothId;
}
