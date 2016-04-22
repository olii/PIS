package pis.back;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig filterConfig;
	
	@Override
	public void destroy() {	
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest)req;
		HttpSession session = httpReq.getSession();
		AuthBean authBean = (AuthBean)session.getAttribute("authBean");
 
		boolean noRedirect = httpReq.getRequestURI().contains("/pis/login.xhtml");
		if (noRedirect || (authBean != null && authBean.isLoggedIn())) {
			chain.doFilter(req, resp);
			return;
		}

		((HttpServletResponse)resp).sendRedirect(httpReq.getContextPath() + "/login.xhtml");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
