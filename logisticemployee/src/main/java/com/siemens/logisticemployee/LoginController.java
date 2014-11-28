package com.siemens.logisticemployee;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.siemens.ct.ro.transportation.dao.EmployeeUserDao;
import com.siemens.ct.ro.transportation.entities.EmployeeUser;

/**
 * Controller used for logging into the application. A default user client is
 * added at the context startup. Any other client must be add manually in the
 * database.
 * 
 * @author Anca Petrescu
 * 
 */
@Controller
public class LoginController {

	@Autowired
	EmployeeUserDao employeeUserDao;

	@Transactional
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, HttpServletResponse response,
			ModelMap modelMap, @RequestParam(required = true) String username,
			@RequestParam(required = true) String password) {

		EmployeeUser user = employeeUserDao.login(username, password);

		if (user == null) {		
			return "home";
		} else {
			session.setAttribute("user", user);
			return "menu";
		}
	}

	@Transactional
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletResponse response) {

		session.removeAttribute("user");
		return "home";
	}
}
