package com.astu.JMEE_JLEE.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactRouterController {
	
	 @RequestMapping(value = {
	            "/ASTU_JMEE_JLEE",
	            "/ASTU_JMEE_JLEE/Registration",
	            "/ASTU_JMEE_JLEE/Login",
	            "/ASTU_JMEE_JLEE/Forgot_Password",
	            "/centredu"
	    })
	    public String redirect() {
	        return "forward:/index.html";
	    }

}

