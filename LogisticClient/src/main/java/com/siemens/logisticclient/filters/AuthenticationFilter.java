package com.siemens.logisticclient.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.siemens.ct.ro.transportation.entities.ClientUser;

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
		ClientUser user = (ClientUser) request.getSession()
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

		if (url.equals("/logisticclient/menu"))
			return false;
		if (url.equals("/logisticclient/viewDashboard"))
			return false;
		if (url.equals("/logisticclient/addOrder"))
			return false;
		if (url.equals("/logisticclient/getOrderId"))
			return false;
		if (url.equals("/logisticclient/getPackages"))
			return false;
		if (url.equals("/logisticclient/getProductTypes"))
			return false;

		return true;
	}
}
