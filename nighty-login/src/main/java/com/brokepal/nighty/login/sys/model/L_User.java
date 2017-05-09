package com.brokepal.nighty.login.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class L_User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected String nickname;
    protected String username;
    protected String password;
    protected String salt;
    protected String email;
    protected String phone;
    protected List<L_Role> roles;
    protected List<L_Resource> resources;
    protected Date createTime;//注册时间
    protected Date lastLoginTime;//注册时间

    public L_User() {}

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<L_Role> getRoles() {
        return roles;
    }

    public void setRoles(List<L_Role> roles) {
        this.roles = roles;
    }

    public List<L_Resource> getResources() {
        return resources;
    }

    public void setResources(List<L_Resource> resources) {
        this.resources = resources;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Date getRegisterTime() {
        return createTime;
    }

    public void setRegisterTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "L_User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }

    public static class Builder{
        private String id;
        private String nickname;
        private String username;
        private String password;
        private String salt;
        private String email;
        private String sex;
        private String phone;
        private List<L_Role> roles;
        private List<L_Resource> resources;
        private Date createTime;
        private Date lastLoginTime;

        public Builder id(String val){
            id = val;
            return this;
        }
        public Builder nickname(String val){
            nickname = val;
            return this;
        }
        public Builder username(String val){
            username = val;
            return this;
        }
        public Builder password(String val){
            password = val;
            return this;
        }
        public Builder salt(String val){
            salt = val;
            return this;
        }
        public Builder email(String val){
            email = val;
            return this;
        }
        public Builder phone(String val){
            phone = val;
            return this;
        }
        public Builder roles(List<L_Role> val){
            roles = val;
            return this;
        }
        public Builder resources(List<L_Resource> val){
            resources = val;
            return this;
        }
        public Builder createTime(Date val){
            createTime = val;
            return this;
        }
        public Builder lastLoginTime(Date val){
            lastLoginTime = val;
            return this;
        }

        public L_User build(){
            L_User user = new L_User();
            user.id = this.id;
            user.nickname = this.nickname;
            user.username = this.username;
            user.password = this.password;
            user.salt = this.salt;
            user.email = this.email;
            user.phone = this.phone;
            user.roles = this.roles;
            user.resources = this.resources;
            user.createTime = this.createTime;
            user.lastLoginTime = this.lastLoginTime;
            return user;
        }
    }
}
