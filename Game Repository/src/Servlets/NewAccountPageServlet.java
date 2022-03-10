package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import Models.User;
import Models.UserList;

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
		
		UserList list = new UserList();
		
			String username = req.getParameter("Username");
			String password = req.getParameter("Password");
			String confirmpass = req.getParameter("PassConfirm");
			
			if(!list.getList().containsKey(username)) {
				if(password == confirmpass) {
					list.createUser(username, password);
				}
				else {
					System.out.println("Passwords must match");
				}
			}
			else {
				System.out.println("Username is taken");
			}
			
			req.setAttribute("Username", req.getParameter("Username"));
			req.setAttribute("Password", req.getParameter("Password"));
			req.setAttribute("ConfirmPass", req.getParameter("ConfirmPass"));
		
	}
	
}