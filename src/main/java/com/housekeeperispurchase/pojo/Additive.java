package com.housekeeperispurchase.pojo;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Additive对象", description="")
public class Additive implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "additiveId", type = IdType.AUTO)
    @ApiModelProperty(value = "附加成本Id")
    private Integer additiveId;

    @ApiModelProperty(value = "附加成本名字")
    private String additiveName;

    @ApiModelProperty(value = "附加成本价格")
    private Integer additivePrice;


}
