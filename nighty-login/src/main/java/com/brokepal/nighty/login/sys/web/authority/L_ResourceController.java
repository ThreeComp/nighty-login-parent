package com.brokepal.nighty.login.sys.web.authority;

import com.brokepal.nighty.login.core.dto.OperationResult;
import com.brokepal.nighty.login.sys.service.L_ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by chenchao on 17/5/9.
 */

@Controller
@RequestMapping(value = "resource")
public class L_ResourceController {
    @Autowired
    private L_ResourceService resourceService;

    @RequestMapping(value = "/allAdminResources")
    @ResponseBody
    public ResponseEntity logout(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");

        return new ResponseEntity(OperationResult.buildSuccessResult(resourceService.getAllAdminResources()), HttpStatus.OK);
    }
}
