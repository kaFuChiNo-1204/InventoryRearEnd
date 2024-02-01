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
@ApiModel(value="Goodstypeone对象", description="")
public class Goodstypeone implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "一级Id")
    @TableId(value = "goodsTypeOneId", type = IdType.AUTO)
    private Integer goodsTypeOneId;

    @ApiModelProperty(value = "一级名字")
    private String goodsTypeOneName;


}
