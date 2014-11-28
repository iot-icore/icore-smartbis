package com.siemens.logisticclient.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.siemens.ct.ro.transportation.dao.ClientUserDao;
import com.siemens.ct.ro.transportation.entities.ClientUser;

/**
 * ServletContextListener implementation.
 * 
 * @author Anca Petrescu
 * 
 */
public class StartupListener implements ServletContextListener {

	/**
	 * Notification that the servlet context is about to be shut down. All
	 * servlets and filters have been destroy()ed before any
	 * ServletContextListeners are notified of context destruction.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		

	}

	/**
	 * Notification that the web application initialization process is starting.
	 * All ServletContextListeners are notified of context initialization before
	 * any filter or servlet in the web application is initialized.
	 */
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		/* get the application context */
		WebApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(contextEvent.getServletContext());
		/* get dao */
		ClientUserDao userdAO = (ClientUserDao) applicationContext
				.getBean("clientUserDao");
		if (!userdAO.exist("user")){
		/* add default user to database if not already exists */
		userdAO.add(new ClientUser("user","password","user name"));
		
		}
		
	}

}
