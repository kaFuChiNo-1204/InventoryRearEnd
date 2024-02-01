package com.housekeeperispurchase.pojo;

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
@ApiModel(value="Finance对象", description="")
public class Finance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "财务Id")
    @TableId(value = "financeId", type = IdType.AUTO)
    private Integer financeId;

    @ApiModelProperty(value = "0:支出,1:收入")
    private String financeType;

    @ApiModelProperty(value = "支出或者收入类别")
    private Integer incomeId;

    @ApiModelProperty(value = "余额")
    private String financeBalance;

    @ApiModelProperty(value = "备注")
    private String financeNotes;

    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "时间")
    private LocalDateTime financeTime;

    @ApiModelProperty(value = "摘要(名字+编码，或者选填)")
    private String financeName;

    @ApiModelProperty(value = "涉及人名字,操作人员（入库Or销售）")
    private Integer financeUserId;

    @ApiModelProperty(value = "图片")
    private String financeImgs;


}
