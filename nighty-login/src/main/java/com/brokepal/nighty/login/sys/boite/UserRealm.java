package com.brokepal.nighty.login.sys.boite;

import com.brokepal.boite.core.realm.AbstractRealm;
import com.brokepal.boite.exception.AuthenticationException;
import com.brokepal.boite.exception.IncorrectCredentialsException;
import com.brokepal.boite.exception.UnknownAccountException;
import com.brokepal.boite.web.util.SecurityUtil;
import com.brokepal.nighty.login.sys.model.L_Resource;
import com.brokepal.nighty.login.sys.model.L_User;
import com.brokepal.nighty.login.sys.persist.L_UserDao;
import com.brokepal.nighty.login.sys.service.L_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenchao on 17/5/31.
 */
public class UserRealm extends AbstractRealm {
    @Autowired
    private L_UserService service;

    @Override
    public void authenticate(String username, String password) throws AuthenticationException {
        L_User user = service.getUserByUsernameOrEmail(username);
        if (user == null){
            throw new UnknownAccountException();
        }
        String salt = user.getSalt();
        String md5Password = SecurityUtil.MD5EncodePassword(password,salt);
        if (!md5Password.equals(user.getPassword()))
            throw new IncorrectCredentialsException();
    }

    @Override
    public Set<String> authorize(String username) {
        Set<String> permissions = new HashSet<String>();
        L_User user = service.getUserByUsername(username);
        List<L_Resource> resources = user.getResources();
        for (L_Resource res : resources) {
            permissions.add(res.getName());
        }
        return permissions;
    }
}
