package com.muchen.mc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName T_ROLE_PERMISSION
 */
@TableName(value ="T_ROLE_PERMISSION")
@Data
public class TRolePermission implements Serializable {
    /**
     * 
     */
    @TableField(value = "rid")
    private Integer rid;

    /**
     * 
     */
    @TableField(value = "pid")
    private Integer pid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}