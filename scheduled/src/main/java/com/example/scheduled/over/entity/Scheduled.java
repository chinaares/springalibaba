package com.example.scheduled.over.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Scheduled {

    @ApiModelProperty(value = "任务ID")
    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;  

    @ApiModelProperty(value = "bean名称")
    @NotEmpty
    private String beanName;

    @ApiModelProperty(value = "方法名称")
    @NotEmpty
    private String methodName;

    @ApiModelProperty(value = "方法参数")
    private String methodParams;  

    @ApiModelProperty(value = "cron表达式")
    @NotEmpty
    private String cronExpression;  

    @ApiModelProperty(value = "状态（1正常 0暂停）")
    @NotNull
    private Integer jobStatus;

    @ApiModelProperty(value = "备注")
    private String remark;  

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
  
}  