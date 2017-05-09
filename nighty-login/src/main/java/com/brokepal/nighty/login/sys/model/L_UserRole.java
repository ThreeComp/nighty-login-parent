package com.brokepal.nighty.login.sys.model;

/**
 * Created by chenchao on 17/4/17.
 */
public class L_UserRole {
    private static final long serialVersionUID = 5L;

    private String id;
    private String roleId;
    private String userId;

    public L_UserRole() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class Builder{
        private String id;
        private String roleId;
        private String userId;

        public Builder id(String val){
            id = val;
            return this;
        }
        public Builder roleId(String val){
            roleId = val;
            return this;
        }
        public Builder userId(String val){
            userId = val;
            return this;
        }
        public L_UserRole build(){
            L_UserRole userRole = new L_UserRole();
            userRole.id = this.id;
            userRole.roleId = this.roleId;
            userRole.userId = this.userId;
            return userRole;
        }
    }

}
