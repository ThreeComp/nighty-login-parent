package com.brokepal.nighty.login.sys.service;

import com.brokepal.nighty.login.sys.model.L_Resource;
import com.brokepal.nighty.login.sys.persist.L_ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by chenchao on 17/5/9.
 */
@Service
@Transactional(propagation= Propagation.REQUIRES_NEW,readOnly=false, timeout=3)
public class L_ResourceService {

    @Autowired
    private L_ResourceDao resourceDao;

    public List<L_Resource> getAllAdminResources(){
        return resourceDao.findList(new L_Resource.Builder().type("1").build());
    }

}
