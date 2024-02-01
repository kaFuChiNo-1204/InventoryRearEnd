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
@ApiModel(value="Income对象", description="")
public class Income implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支出收入类别Id")
    @TableId(value = "incomeId", type = IdType.AUTO)
    private Integer incomeId;

    @ApiModelProperty(value = "支出OR收入")
    private String incomeType;

    @ApiModelProperty(value = "支持收入类别名字")
    private String incomeName;


}
