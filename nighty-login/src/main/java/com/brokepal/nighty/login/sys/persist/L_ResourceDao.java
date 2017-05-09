package com.brokepal.nighty.login.sys.persist;


import com.brokepal.nighty.login.sys.model.L_Resource;

import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface L_ResourceDao {
    L_Resource get(String id);
    List<L_Resource> findList(L_Resource resource);
}
