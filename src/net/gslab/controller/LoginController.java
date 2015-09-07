package net.gslab.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.gslab.entity.Member;
import net.gslab.entity.User;
import net.gslab.service.MemberService;
import net.gslab.service.UserService;





import net.gslab.setting.CommonConstant;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/view")
public class LoginController extends BaseController{
	
	@Resource(name="memberServiceImpl")
	private MemberService memberService;

	@RequestMapping(value="/doLogin",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,Member member){
		//System.out.println("in the login");
		
		Member dbMember = memberService.getMemberByLoadName(member.getLoadname());
		ModelAndView mav = new ModelAndView();
		if(dbMember==null) {
			mav.addObject(ERROR_MSG_KEY, "用户名不存在");
			mav.setViewName("redirect:/common/resource_not_found.jsp");
			}
		else if(!dbMember.getPassword().equals(member.getPassword())){
			mav.addObject(ERROR_MSG_KEY,"用户密码不正确");
			mav.setViewName("redirect:/common/resource_not_found.jsp");
		}else{
			memberService.loginSuccess(dbMember);
			mav.addObject(ERROR_MSG_KEY, "您已成功登陆");
			this.setSessionMember(request, dbMember);
			String toUrl=(String)request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
			//toUrl要访问的页面，由于拦截器，如果不能通过拦截器不能访问到正确的页面
			request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);
			mav.setViewName("home");//逻辑视图，与applicationContext-mvc下定义的viewResolver对应，不光前面，还有后面的Jsp也不能加
			//System.out.println("用户申请跳转到页面："+toUrl);
			if(StringUtils.isEmpty(toUrl)){
				toUrl="/view/home.jsp";
			}
			mav.setViewName("redirect:"+toUrl);
		}
		//mav.setViewName("mHome");
		
		return mav;
	}
}
