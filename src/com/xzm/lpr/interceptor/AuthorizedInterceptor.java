package com.xzm.lpr.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xzm.lpr.domain.User;
import com.xzm.lpr.util.common.LprConstants;

public class AuthorizedInterceptor  implements HandlerInterceptor {

	private static final String[] IGNORE_URI = {"/loginform", "/login","/upload","/404.html"};
	 
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mv) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		/** 默认用户没有登录 */
		boolean flag = false; 
		/** 获得请求的ServletPath */
		String servletPath = request.getServletPath();
		/**  判断请求是否需要拦截 */
        for (String s : IGNORE_URI) {
            if (servletPath.contains(s)) {
                flag = true;
                break;
            }
        }
        
        if (!flag){
        	User user = (User) request.getSession().getAttribute(LprConstants.USER_SESSION);
        	if(user == null){
        		request.setAttribute("message", "请先登录再访问网站!");
        		request.getRequestDispatcher(LprConstants.LOGIN).forward(request, response);
        		return flag;
        	}else{
        		 flag = true;
        	}
        }
        return flag;
		
	}

}
