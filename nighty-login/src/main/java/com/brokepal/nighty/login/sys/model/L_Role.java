package com.brokepal.nighty.login.sys.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenchao on 17/4/17.
 */
public class L_Role implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String description;
    private String type;
    private List<L_Resource> resources;

    public L_Role() {}

    public List<L_Resource> getResources() {
        return resources;
    }

    public void setResources(List<L_Resource> resources) {
        this.resources = resources;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class Builder{
        private String id;
        private String name;
        private String description;
        private String type;
        private List<L_Resource> resources;

        public Builder id(String val){
            id = val;
            return this;
        }
        public Builder name(String val){
            name = val;
            return this;
        }
        public Builder description(String val){
            description = val;
            return this;
        }
        public Builder type(String val){
            type = val;
            return this;
        }
        public Builder resources(List<L_Resource> val){
            resources = val;
            return this;
        }
        public L_Role build(){
            L_Role role = new L_Role();
            role.id = this.id;
            role.name = this.name;
            role.description = this.description;
            role.type = this.type;
            role.resources = this.resources;
            return role;
        }
    }
}
