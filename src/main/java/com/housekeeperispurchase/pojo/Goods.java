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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@ApiModel(value="Goods对象", description="")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id，自动")
    @TableId(value = "goodsId", type = IdType.AUTO)
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称(名称+编码)")
    private String goodsName;

    @ApiModelProperty(value = "内码，编码")
    private String goodsCode;

    //    进行时间的转换
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "当前时间")
    private LocalDateTime goodsTime;

    @ApiModelProperty(value = "数量")
    private Integer goodsNumber;

    @ApiModelProperty(value = "商品图片")
    private String goodsImgs;

    @ApiModelProperty(value = "入库员工ID")
    private Integer goodsPushUserId;

    @ApiModelProperty(value = "大类别Id")
    private Integer goodsTypeOneId;

    @ApiModelProperty(value = "中类别Id")
    private Integer goodsTypeTwoId;

    @ApiModelProperty(value = "小类别Id")
    private Integer goodsTypeThreeId;

    @ApiModelProperty(value = "入库价格(成本)")
    private BigDecimal goodsPushPrice;

    @ApiModelProperty(value = "预定卖价")
    private BigDecimal goodsDuePrice;

    @ApiModelProperty(value = "备注(借出人员)")
    private String goodsNotes;

    @ApiModelProperty(value = "附加成本Id")
    private String additiveId;

    @ApiModelProperty(value = "是否禁用")
    private String goodsDisabled;


}
