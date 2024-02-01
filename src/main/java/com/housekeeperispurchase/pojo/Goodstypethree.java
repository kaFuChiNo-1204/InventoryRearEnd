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
@ApiModel(value="Goodstypethree对象", description="")
public class Goodstypethree implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "三级Id")
    @TableId(value = "goodsTypeThreeId", type = IdType.AUTO)
    private Integer goodsTypeThreeId;

    @ApiModelProperty(value = "三级名字")
    private String goodsTypeThreeName;

    @ApiModelProperty(value = "二级Id")
    private Integer goodsTypeTwoId;


}
