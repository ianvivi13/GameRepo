package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import Models.User;

public class NewAccountPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("New Account Servlet: doGet");
		
		req.getRequestDispatcher("_view/newaccountpage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
	
}