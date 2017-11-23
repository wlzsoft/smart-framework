package com.smartframe.basics.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Login_Token_Interceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Login_Token_Interceptor.class);

	//在处理器调用之前被调用
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		//获取请求的URL 
		String url = request.getRequestURI(); 
		if(url.indexOf("/login")>=0){ 
		    return true;    
		}else{
			String token = response.getHeader("token");
			
			if(null==token||token.equals("")){
				LOGGER.warn("Token获取为null");
				response.setStatus(401);
				return false;
			}
			
			HttpSession session = request.getSession(); 
			String tokens = (String)session.getAttribute(token); //获取服务端token是否存在
			
			if(null==tokens||tokens.equals("")){
				response.setStatus(401);
				return false;
			}
			return true;
		}
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
