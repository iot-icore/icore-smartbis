package com.siemens.transportationcvomng.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
 
/**
 * Filter that intercepts the requests and add the header information required
 * for the CORS to work.The headers to note are Access-Control-Allow-Origin and
 * Access-Control-Allow-Headers
 * 
 * @author dragos_damian
 * 
 */
public class CorsFilter extends OncePerRequestFilter {
 
 @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

	 		//To allow access from all domains
     		response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
            response.addHeader("Access-Control-Max-Age", "1800");//<span class="IL_AD" id="IL_AD3">30 min</span>

        filterChain.doFilter(request, response);
    }
}
