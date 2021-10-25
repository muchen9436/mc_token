package com.muchen.mc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_user
 */
@TableName(value ="t_user")
@Data
public class TUser implements Serializable {
    /**
     * 
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField(value = "USERNAME")
    private String username;

    /**
     * 
     */
    @TableField(value = "PASSWD")
    private String passwd;

    /**
     * 
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;

    /**
     * 
     */
    @TableField(value = "STATUS")
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}