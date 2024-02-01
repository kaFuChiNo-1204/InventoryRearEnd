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
@ApiModel(value="Account对象", description="")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号Id")
    @TableId(value = "accountId", type = IdType.AUTO)
    private Integer accountId;

    @ApiModelProperty(value = "账号")
    private String accountUser;

    @ApiModelProperty(value = "密码")
    private String accountPass;


}
