package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;
import Models.User;

public class LoginPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
				
			if(req.getParameter("logout") != null ){  
				resp.sendRedirect("../gamerepo/login");
		        session.invalidate();
		        return;
			}
		
		System.out.println("Login Servlet: doGet");
		
		req.getRequestDispatcher("_view/loginpage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = new User(null, null);
		String username = req.getParameter("Username");
		String password = req.getParameter("Password");
		
		System.out.println("Verifying User: " + username);
		
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		if (db.login(username, password)) {
			user.setUsername(username);
			user.setPassword(password);
			//System.out.println(user.getUsername());
			
			req.getSession().setAttribute("user", username);
			System.out.println("Authenticated user: " + username);
			resp.sendRedirect("../gamerepo/home");
			
		} else {
			System.out.println("Failed to authenticate user: " + username);
			
			PrintWriter out = resp.getWriter(); 
			out.println("<script type=\"text/javascript\">"); 
			out.println("alert('There is an invalid entry for password or username');"); 
			out.println("location='../gamerepo/login';"); 
			out.println("</script>");
		}
	}
	
}