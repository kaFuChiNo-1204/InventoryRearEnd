package com.housekeeperispurchase.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@ApiModel(value="Profit对象", description="")
public class Profit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "利润Id")
    @TableId(value = "profitId", type = IdType.AUTO)
    private Integer profitId;

    @ApiModelProperty(value = "人员 ")
    private Integer profitUserId;

    @ApiModelProperty(value = "明细名称")
    private String profitName;

    @ApiModelProperty(value = "利润价格")
    private BigDecimal profitPrice;

    @ApiModelProperty(value = "区别(销售or回收)")
    private String profitType;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "时间(销售or回收)")
    private LocalDateTime profitTime;

    @ApiModelProperty(value = "业绩价格")
    private String profitOutStanding;


}
