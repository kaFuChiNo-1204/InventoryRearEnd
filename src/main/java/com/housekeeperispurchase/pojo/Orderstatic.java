package com.housekeeperispurchase.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yang
 * @since 2023-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Orderstatic对象", description="")
public class Orderstatic implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单状态Id")
    @TableId(value = "orderStaticId", type = IdType.AUTO)
    private Integer orderStaticId;

    @ApiModelProperty(value = "订单状态内容(已结，未结，退单)")
    private String orderStaticName;


}
