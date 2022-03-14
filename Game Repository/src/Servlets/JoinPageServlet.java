package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Models.UserList;

public class JoinPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Join Servlet: doGet");
		
		req.getRequestDispatcher("_view/join.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		UserList list = new UserList();
		
		String username = list.getUser("admin").getUsername();
		System.out.println("Username: " + username);
		
		req.setAttribute("username", username);
		
		req.getRequestDispatcher("_view/homepage.jsp").forward(req, resp);
	}
	
}