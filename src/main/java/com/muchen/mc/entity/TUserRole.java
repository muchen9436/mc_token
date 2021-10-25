package com.muchen.mc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName T_USER_ROLE
 */
@TableName(value ="T_USER_ROLE")
@Data
public class TUserRole implements Serializable {
    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "tid")
    private Integer tid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}