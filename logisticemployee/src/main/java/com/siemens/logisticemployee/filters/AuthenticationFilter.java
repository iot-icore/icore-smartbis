package com.siemens.logisticemployee.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.siemens.ct.ro.transportation.entities.EmployeeUser;

/**
 * Check if the client is logged in, else redirect to login page.
 * 
 * @author Anca Petrescu
 * 
 */
public class AuthenticationFilter extends OncePerRequestFilter {

	/**
	 * Automatically called when a request is received. Checks if a user is
	 * logged in the current session if not then redirect to login page.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		/**
		 * Get the request uri.
		 */
		String url = request.getRequestURI();
		/**
		 * Get the user logged in the current session.
		 */
		EmployeeUser user = (EmployeeUser) request.getSession()
				.getAttribute("user");

		/**
		 * Check if the request uri needs authentication.
		 */
		if (ignore(url)) {
			filterChain.doFilter(request, response);
			return;
		}
		/**
		 * Check if valid user logged in.
		 */
		if (user != null) {
			filterChain.doFilter(request, response);

		} else {
			response.sendRedirect("redirect");
		}

	}

	/**
	 * 
	 * @param url
	 * @return true if the request url can be ignored <br>
	 * 		   false if the user has to be logged in to see the requested page
	 */
	private boolean ignore(String url) {

		if (url.equals("/logisticemployee/menu"))
			return false;
		if (url.equals("/logisticemployee/getSensors"))
			return false;
		if (url.equals("/logisticemployee/getOrders"))
			return false;
		if (url.equals("/logisticemployee/addSingleSensor"))
			return false;
		if (url.equals("/logisticemployee/bind"))
			return false;
		if (url.equals("/logisticemployee/unbind"))
			return false;
		if (url.equals("/logisticemployee/getPackages"))
			return false;
		if (url.equals("/logisticemployee/getBoundPackages"))
			return false;
		if (url.equals("/logisticemployee/start"))
			return false;
		if (url.equals("/logisticemployee/binding"))
			return false;
		if (url.equals("/logisticemployee/unbinding"))
			return false;
		if (url.equals("/logisticemployee/addSensor"))
			return false;
		if (url.equals("/logisticemployee/managepm"))
			return false;

		return true;
	}
}
