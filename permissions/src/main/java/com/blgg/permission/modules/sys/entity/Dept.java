package com.blgg.permission.modules.sys.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@TableName("sys_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;
    /**
     * 上级部门ID，一级部门为0
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 是否删除  -1：已删除  0：正常
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * ztree属性
     */
    @TableField(exist=false)
    private Boolean open;
    @TableField(exist=false)
    private List<?> list;
    //上级部门名称
    @TableField(exist=false)
    private String parentName;

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Dept{" +
        ", deptId=" + deptId +
        ", parentId=" + parentId +
        ", name=" + name +
        ", orderNum=" + orderNum +
        ", delFlag=" + delFlag +
        "}";
    }
}
