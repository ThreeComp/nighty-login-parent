package com.brokepal.nighty.login.sys.service;

import com.brokepal.nighty.login.core.cache.Cache;
import com.brokepal.nighty.login.core.util.CommonUtil;
import com.brokepal.nighty.login.core.util.SendEmail;
import com.brokepal.nighty.login.sys.constant.AccountConstant;
import com.brokepal.nighty.login.sys.model.*;
import com.brokepal.nighty.login.sys.persist.*;
import com.brokepal.nighty.login.sys.storage_object.SendEmailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

/**
 * Created by Administrator on 2017/2/23.
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false, timeout=3)
public class L_UserService {
    @Autowired
    private L_UserDao userDao;
    @Autowired
    private L_RoleDao roleDao;
    @Autowired
    private L_ResourceDao resourceDao;
    @Autowired
    private L_RoleResourceDao roleResourceDao;
    @Autowired
    private L_UserRoleDao userRoleDao;

    /**
     * 创建用户
     * @param user
     * @return int 成功则返回1，失败则返回0
     * @throws IllegalArgumentException 参数user不能为null，user的nickname、username,password,email都不能为null
     */
    public int createUser(L_User user) {
        if (user == null)
            throw new IllegalArgumentException("user can't be null");
        if (user.getNickname() == null || user.getUsername() == null || user.getPassword() == null || user.getEmail() == null || user.getSalt() == null)
            throw new IllegalArgumentException("nickname,username,password,email and salt can't be null");
        if (user.getId() == null)
            user.setId(UUID.randomUUID().toString());
        try {
            int count = userDao.createUser(user);
            for (L_Role role : user.getRoles()){
                userRoleDao.createRoleUser(new L_UserRole.Builder().id(UUID.randomUUID().toString()).roleId(role.getId()).userId(user.getId()).build());
            }
            if (count > 0){
                System.out.println("success");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(); //抛出异常，回滚事务
        }
        return 0;
    }

    /**
     * 根据邮箱修改用户密码
     * @param email
     * @param password
     * @return int 成功则返回1，失败则返回0
     */
    public int updatePasswordByEmail(String email, String password) {
        return userDao.updatePassword(email,password);
    }

    public int updateLastLoginTime(String username, Date lastLoginTime){
        return userDao.updateLastLoginTime(username, lastLoginTime);
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return L_User 如果查询到结果，则返回，否则，返回null
     * @throws IllegalArgumentException username不能为null或空字符串
     */
    public L_User getUserByUsername(String username) {
        if (username == null || "".equals(username))
            throw new IllegalArgumentException("username can't be null or empty");
        try {
            L_User user = userDao.getUserByUsername(username);
            putRoleAndResourceToUser(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据邮箱查询用户
     * @param emial
     * @return L_User 如果查询到结果，则返回，否则，返回null
     * @throws IllegalArgumentException username不能为null或空字符串
     */
    public L_User getUserByEmail(String emial) {
        if (emial == null || "".equals(emial))
            throw new IllegalArgumentException("emial can't be null or empty");
        try {
            L_User user = userDao.getUserByEmail(emial);
            putRoleAndResourceToUser(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDefaultRolesAndResToUser(L_User user){
        List<L_Role> roles = roleDao.findList(new L_Role.Builder().name("guest").build());
        List<L_Resource> resources = new ArrayList<L_Resource>();
        for (L_Role role : roles) {
            List<L_RoleResource> roleResources = roleResourceDao.findList(new L_RoleResource.Builder().roleId(role.getId()).build());
            for (L_RoleResource rs : roleResources) {
                resources.add(resourceDao.get(rs.getResourceId()));
            }
        }
        user.setRoles(roles);
        user.setResources(resources);
    }

    /**
     * 发送重置密码的验证码
     * @param email
     * @return 如果发送成功，则返回验证码，否则返回null
     */
    public String sendValidateCodeEmial(String email) {
        String validateCode = CommonUtil.createRandomString(6);
        ///邮件的内容
        String content = "请在15分钟内输入以下验证码进行重置密码： " + validateCode;
        //发送邮件
        try {
            SendEmail.send(email, content, "重置密码邮件");
            return validateCode;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SendEmailInfo getSendEmailInfo(String email){
        return Cache.get(AccountConstant.RESET_PASSWORD_NAMESPACE, email);
    }

    public void setSendEmailInfo(String email, SendEmailInfo info){
        Cache.put(AccountConstant.RESET_PASSWORD_NAMESPACE, email,info);
    }

    private void putRoleAndResourceToUser(L_User user){
        //获取roles
        List<L_Role> roles = new ArrayList<L_Role>();
        List<L_UserRole> userRoles = userRoleDao.findList(new L_UserRole.Builder().userId(user.getId()).build());
        for (L_UserRole userRole : userRoles) {
            roles.add(roleDao.get(userRole.getRoleId()));
        }
        user.setRoles(roles);
        //获取权限
        Set<L_Resource> resources = new HashSet<L_Resource>();
        for (L_Role role : roles){
            List<L_RoleResource> roleReses = roleResourceDao.findList(new L_RoleResource.Builder().roleId(role.getId()).build());
            for (L_RoleResource roleResource : roleReses) {
                resources.add(resourceDao.get(roleResource.getResourceId()));
            }
        }
        user.setResources(new ArrayList<L_Resource>(resources));
    }



    /******************************** 以下为管理员对应的业务方法 ************************************/

    public List<L_User> findList(L_User filterEntity){
        return userDao.findList(filterEntity);
    }

    public List<L_User> getAll(){
        List<L_User> users = userDao.findList(new L_User());
        for (L_User user : users) {
            putRoleAndResourceToUser(user);
        }
        return users;
    }
}
