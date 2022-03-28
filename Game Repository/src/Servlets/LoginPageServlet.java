package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import Models.User;

public class LoginPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Login Servlet: doGet");
		
		req.getRequestDispatcher("_view/loginpage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username = req.getParameter("Username");
		String password = req.getParameter("Password");
		
		System.out.println("Verify User");
		System.out.println(username);
		System.out.println(password);
		
		if(username.equals("admin") && password.equals("admin")) {
			resp.sendRedirect("http://localhost:8080/gamerepo/home");
			return;
		}
		else {
			resp.sendRedirect("http://localhost:8080/gamerepo/new");
			return;
		}
	}
	
}