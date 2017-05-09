package com.brokepal.nighty.login.sys.service;

import com.brokepal.nighty.login.sys.model.L_Resource;
import com.brokepal.nighty.login.sys.model.L_Role;
import com.brokepal.nighty.login.sys.model.L_RoleResource;
import com.brokepal.nighty.login.sys.persist.L_ResourceDao;
import com.brokepal.nighty.login.sys.persist.L_RoleDao;
import com.brokepal.nighty.login.sys.persist.L_RoleResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenchao on 17/4/23.
 */
@Service
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false, timeout=3)
public class L_RoleService {
    @Autowired
    private L_RoleDao roleDao;
    @Autowired
    private L_ResourceDao resourceDao;
    @Autowired
    private L_RoleResourceDao roleResourceDao;

    public List<L_Role> getAll(){
        List<L_Role> roles = roleDao.findList(new L_Role());
        for (L_Role role : roles){
            List<L_Resource> resources = new ArrayList<L_Resource>();
            List<L_RoleResource> roleResources = roleResourceDao.findList(new L_RoleResource.Builder().roleId(role.getId()).build());
            for (L_RoleResource roleResource : roleResources) {
                resources.add(resourceDao.get(roleResource.getResourceId()));
            }
            role.setResources(resources);
        }
        return roles;
    }
}
