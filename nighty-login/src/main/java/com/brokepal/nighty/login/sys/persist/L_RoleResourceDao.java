package com.brokepal.nighty.login.sys.persist;

import com.brokepal.nighty.login.sys.model.L_RoleResource;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface L_RoleResourceDao {
    List<L_RoleResource> findList(L_RoleResource roleResource);
}
