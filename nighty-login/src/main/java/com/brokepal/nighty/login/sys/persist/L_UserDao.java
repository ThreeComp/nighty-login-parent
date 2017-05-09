package com.brokepal.nighty.login.sys.persist;

import com.brokepal.nighty.login.sys.model.L_User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public interface L_UserDao {
    int createUser(L_User user);
    int updatePassword(@Param("email") String email, @Param("password") String password);
    int updateLastLoginTime(@Param("username") String username, @Param("lastLoginTime") Date lastLoginTime);
    L_User getUserByUsername(String username);
    L_User getUserByEmail(String email);


    /******************************** 以下为管理员对应的业务方法 ************************************/

    public List<L_User> findList(L_User filterEntity);
}
