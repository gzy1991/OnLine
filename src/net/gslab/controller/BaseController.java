package net.gslab.controller;

import javax.servlet.http.HttpServletRequest;

import net.gslab.entity.Member;
import net.gslab.entity.User;
import net.gslab.setting.CommonConstant;

public class BaseController {
	protected static final String ERROR_MSG_KEY="errorMsg";
	
	protected User getSessionUser(HttpServletRequest request){
		return (User)request.getSession().getAttribute(CommonConstant.USER_CONTEXT);
	}
	
	protected void setSessionMember(HttpServletRequest request,Member member){
		request.getSession().setAttribute(CommonConstant.MEMBER_CONTEXT, member);
	}

}
