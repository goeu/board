package org.zerock.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BasicController
 */
public abstract class BasicController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req
			, HttpServletResponse res) throws ServletException, IOException {
		
		String path = req.getRequestURI(); //PATH: /board/register 
		
		System.out.println("PATH: " + path);
		
		String methodType = req.getMethod(); // Method: GET 
		
		System.out.println("Method: " + methodType);
		
		Method[] methods = this.getClass().getDeclaredMethods(); //Service
		
		Method targetMethod = null;
		
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i]; //service
			RequestMapping anno =
					m.getDeclaredAnnotation(RequestMapping.class); //ReqM의 anno
			
			String annoPath = anno.value();
			String annoType = anno.type();
			
			if (path.equals(annoPath) && (annoType.equals(methodType))) {
				targetMethod = m; //Service
				break; //메소드 다 찾으면 탈출
			}
			
		}
		
		try {
			String result = (String)targetMethod.invoke(this, req, res); //메소드 호출 service 호출
			
			if(result.startsWith("redirect:")) { 
				res.sendRedirect(result.substring(10)); //redirect: 날림
				
			} else {
				req.getRequestDispatcher("/WEB-INF/views" + result + ".jsp")
				.forward(req, res);
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	protected Cookie checkCookieExist(HttpServletRequest req, String cookieName) {
		//쿠키 생성
		Cookie[] cks = req.getCookies();
		//쿠키가 생성되지 않았으면 null
		if(cks == null || cks.length==0) {
			return null;
		}
		Optional<Cookie> result = Arrays.stream(cks)
				.filter(ck -> ck.getName().equals(cookieName)).findFirst();
		
		
		return result.isPresent()?result.get():null;
	}
	

	protected boolean checkCookieValue(Cookie targetCookie, String parameter, String token) {
		if(targetCookie==null || targetCookie.getValue().length() <= 1) {
			return false;
		}
		
		String[] arr = targetCookie.getValue().split(token);
		
		System.out.println(Arrays.toString(arr));
		
		Optional<String> result = Arrays.stream(arr).filter(str -> str.equals(parameter)).findFirst();
		
		return result.isPresent();
	}

}