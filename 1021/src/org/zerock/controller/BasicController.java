package org.zerock.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BasicController
 */

public abstract class BasicController extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, 
			HttpServletResponse res) throws ServletException, IOException {

		String path = req.getRequestURI();
		
		System.out.println("PATH: " + path);
		
		String methodType = req.getMethod();
		
		System.out.println("Method: " + methodType);
		
		Method[] methods = this.getClass().getDeclaredMethods();
		Method targetMethod = null;
		
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			RequestMapping anno 
			 = m.getDeclaredAnnotation(RequestMapping.class);
			
			String annoPath = anno.value();
			String annoType = anno.type();
			
			if( path.equals(annoPath) && 
					(annoType.contentEquals(methodType)) ) {
				
				targetMethod = m;
				
				break;
			}
		}
		
		
		try {
			
			String result = (String)targetMethod.invoke(this, req, res);
			
			
			if(result.startsWith("redirect:")){
				
				res.sendRedirect(result.substring(10));
				
			}else {
				req.getRequestDispatcher("/WEB-INF/views"+result +".jsp")
				.forward(req, res);
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
		
	}
}
