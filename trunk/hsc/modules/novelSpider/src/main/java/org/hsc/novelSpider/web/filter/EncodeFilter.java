package org.hsc.novelSpider.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodeFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;
//	private final static Logger log=LoggerFactory.getLogger(EncodeFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		 HttpServletRequest httpRequest = (HttpServletRequest) request;  
		 HttpServletResponse httpResponse = (HttpServletResponse) response;  
		 String uri=httpRequest.getRequestURI();
		
		 
		String contentType="";
		 if(uri.lastIndexOf(".js")>0){
			 contentType="application/javascript;charset=utf8";
		 }else if(uri.lastIndexOf(".html")>0){
			 contentType="text/html;charset=utf8";
		 }
		 response.setContentType(contentType);
		 
		 
		 request.setCharacterEncoding("UTF-8");
		 filterChain.doFilter(httpRequest, httpResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}