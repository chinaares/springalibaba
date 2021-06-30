package com.example.jwt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wh
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysPermission对象", description="")
public class SysPermission implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "标识主键")
    private String code;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "功能说明")
    private String description;

    @ApiModelProperty(value = "父id")
    private Integer pid;

    @ApiModelProperty(value = "url路径")
    private String url;

    @ApiModelProperty(value = "方法名")
    private String method;

    @ApiModelProperty(value = "是否可用，0可用，1禁用")
    private Integer state;

    @ApiModelProperty(value = "权限标识")
    private String key;


}
