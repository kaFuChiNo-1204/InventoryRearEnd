package com.housekeeperispurchase.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel(value="Order对象", description="")
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单Id")
    @TableId(value = "orderId", type = IdType.AUTO)
    private Integer orderId;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "订单图片")
    private String orderImgs;

    @ApiModelProperty(value = "订单名称")
    private String orderName;

    @ApiModelProperty(value = "订单编码")
    private String orderCode;

    @ApiModelProperty(value = "订单大类别")
    private Integer orderTypeOneId;

    @ApiModelProperty(value = "总成本，商品成本和商品附加成本相加")
    private BigDecimal orderCostPrice;

    @ApiModelProperty(value = "卖价，自己写")
    private BigDecimal orderSellingPrice;

    @ApiModelProperty(value = "利润，计算")
    private BigDecimal orderProfitPrice;

    @ApiModelProperty(value = "销售员Id ")
    private Integer orderSalesUserId;

    @ApiModelProperty(value = "附加成本，附加成本相加")
    private BigDecimal orderAdditivePrice;

    @ApiModelProperty(value = "入库人员")
    private Integer orderPushUserId;

    @ApiModelProperty(value = "订单状态Id")
    private Integer orderStaticId;

    @ApiModelProperty(value = "回收人员Id")
    private Integer orderRecoupUserId;

    @ApiModelProperty(value = "备注")
    private String orderNotes;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "开单时间，默认当前时间，可以修改")
    private LocalDateTime orderPushTime;


}
