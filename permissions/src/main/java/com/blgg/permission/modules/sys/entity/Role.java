package com.blgg.permission.modules.sys.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author xiaobo
 * @since 2018-10-12
 */
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;
    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 部门名称
     */
    @TableField(exist=false)
    private String deptName;

    @TableField(exist=false)
    private List<Long> menuIdList;
    @TableField(exist=false)
    private List<Long> deptIdList;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Role{" +
        ", roleId=" + roleId +
        ", roleName=" + roleName +
        ", remark=" + remark +
        ", deptId=" + deptId +
        ", createTime=" + createTime +
        "}";
    }
}
