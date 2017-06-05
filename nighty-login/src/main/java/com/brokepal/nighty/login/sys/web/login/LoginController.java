package com.brokepal.nighty.login.sys.web.login;

import com.brokepal.boite.exception.*;
import com.brokepal.boite.core.handle.SubjectHandle;
import com.brokepal.boite.core.handle.SubjectHandleUtil;
import com.brokepal.boite.web.util.SecurityUtil;
import com.brokepal.nighty.login.core.dto.OperationResult;
import com.brokepal.nighty.login.core.exception.RequestParamException;
import com.brokepal.nighty.login.core.util.RSACryptoUtil;
import com.brokepal.nighty.login.security.constant.SecurityConstant;
import com.brokepal.nighty.login.security.idto.IsLoginResult;
import com.brokepal.nighty.login.security.service.SecurityService;
//import com.brokepal.nighty.login.security.util.SecurityUtil;
import com.brokepal.nighty.login.sys.boite.UserRealm;
import com.brokepal.nighty.login.sys.dto.LoginSuccessResult;
import com.brokepal.nighty.login.sys.model.L_Role;
import com.brokepal.nighty.login.sys.model.L_User;
import com.brokepal.nighty.login.sys.service.L_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPrivateKey;

/**
 * Created by chenchao on 17/3/28.
 */
@Controller
@RequestMapping(value = "static")
public class LoginController {

    @Autowired
    private L_UserService userService;
    @Autowired
    private SecurityService securityService;

    /**
     * 用户注册接口,请求中必须带以下字段，否则抛出RequestParamException异常
     * @param sessionId
     * @param username
     * @param password
     * @param keepPassword
     * @return
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestParam("sessionId") String sessionId,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password,
                                boolean keepPassword) {
        SubjectHandle subjectHandle = SubjectHandleUtil.createHandle(sessionId);

        if (keepPassword)
            subjectHandle.setKeepPassword(true);

        subjectHandle.openSingleDeviceOn();

        OperationResult result;

        try {
            String srcPassword = subjectHandle.decodePassword(password);//解密密码
            String token = subjectHandle.login(username, srcPassword);
            if (subjectHandle.isAuthenticated()) {
                String roleType = "user";
                L_User user = userService.getUserByUsernameOrEmail(username);
                for (L_Role role : user.getRoles()){
                    if ("1".equals(role.getType())){ //表示该用户是管理员
                        roleType = "admin";
                        break;
                    }
                }
                result = OperationResult.buildSuccessResult(new LoginSuccessResult(token,user.getNickname(),user.getUsername(),roleType,user.getResources()));
            } else
                result = OperationResult.buildFailureResult("身份认证失败");
        } catch (ConnectTimeOutException e) {
            e.printStackTrace();
            result = OperationResult.buildFailureResult("建立连接超时，请刷新页面，重新登录");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            result = OperationResult.buildFailureResult("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            result = OperationResult.buildFailureResult("密码错误");
        } catch (LockedAccountException e) {
            e.printStackTrace();
            result = OperationResult.buildFailureResult("账号锁定");
        }

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/isLogin", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity isLogin(@RequestParam("sessionId") String sessionId, @RequestParam("token") String token) throws NoSubjectHandleException {
        SubjectHandle subjectHandle = SubjectHandleUtil.getSubjectHandle(sessionId);
        boolean isLogin = subjectHandle.isLogin(token);
        OperationResult result;
        if (isLogin){
            String username = SecurityUtil.getUsernameFromToken(token);
            L_User user = userService.getUserByUsername(username);
            //已经登录了
            String roleType = "user";
            for (L_Role role : user.getRoles()){
                if ("1".equals(role.getType())){
                    roleType = "admin";
                    break;
                }
            }
            result = OperationResult.buildSuccessResult(new LoginSuccessResult(token,user.getNickname(),user.getUsername(),roleType,user.getResources()));

        }
        else
            result = OperationResult.buildFailureResult("false");
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/logout")
    @ResponseBody
    public ResponseEntity logout(@RequestParam("sessionId") String sessionId,
                                 HttpServletResponse response) throws NoSubjectHandleException {
        SubjectHandle handle = SubjectHandleUtil.getSubjectHandle(sessionId);
        handle.logout();

        return new ResponseEntity(OperationResult.buildSuccessResult(), HttpStatus.OK);
    }
}
