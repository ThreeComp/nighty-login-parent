package com.brokepal.nighty.login.sys.persist;


import com.brokepal.nighty.login.sys.model.L_UserRole;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface L_UserRoleDao {
    List<L_UserRole> findList(L_UserRole userRole);
    int createRoleUser(L_UserRole userRole);
}
