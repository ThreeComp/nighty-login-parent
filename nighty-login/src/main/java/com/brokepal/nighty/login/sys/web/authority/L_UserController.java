package com.brokepal.nighty.login.sys.web.authority;

import com.brokepal.nighty.login.core.dto.OperationResult;
import com.brokepal.nighty.login.sys.model.L_User;
import com.brokepal.nighty.login.sys.service.L_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenchao on 17/5/9.
 */

@Controller
@RequestMapping(value = "user")
public class L_UserController {
    @Autowired
    private L_UserService userService;

    @RequestMapping(value = "/allUsers")
    @ResponseBody
    public ResponseEntity logout(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");

        return new ResponseEntity(OperationResult.buildSuccessResult(userService.getAll()), HttpStatus.OK);
    }
}
