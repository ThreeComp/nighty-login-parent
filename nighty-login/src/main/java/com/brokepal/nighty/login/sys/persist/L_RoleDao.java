package com.brokepal.nighty.login.sys.persist;


import com.brokepal.nighty.login.sys.model.L_Role;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface L_RoleDao {
    L_Role get(String id);
    List<L_Role> findList(L_Role role);

}
