package com.brokepal.nighty.login;

import com.brokepal.nighty.login.sys.model.L_User;
import com.brokepal.nighty.login.sys.persist.L_UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2016/9/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-context.xml"})
public class UserDaoTest {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private L_UserDao userDao;

    @Test
    public void getUser(){
        L_User user = userDao.getUserByUsername("admin");
        System.out.println(user);
    }

}