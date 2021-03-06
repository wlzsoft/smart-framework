package com.smartframe.basics.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.smartframe.entity.UserCur;

public class Login_Interceptor implements HandlerInterceptor {

	//在处理器调用之前被调用
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		//获取请求的URL 
		String url = request.getRequestURI(); 
		if(url.indexOf("/login")>=0){ 
		    return true;    
		}
		HttpSession session = request.getSession(); 
		UserCur userCur = (UserCur)session.getAttribute("userCur"); 
		if(userCur != null){
		  return true; 
		}
		response.setStatus(401);
		return false;
	}

	//在处理器调用之后执行
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
		
	}

	//在请求结束之后调用
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
	

}
