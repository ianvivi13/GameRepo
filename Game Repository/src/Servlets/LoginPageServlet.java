package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;
import Models.Pair;
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
		
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		
		if(db.login(username, password)) {
			resp.sendRedirect("http://localhost:8080/gamerepo/home");
			return;
		}
		else {
			 
			PrintWriter out = resp.getWriter(); 
			out.println("<script type=\"text/javascript\">"); 
			out.println("alert('There is an invalid entry for password or username');"); 
			out.println("location='http://localhost:8080/gamerepo/login';"); 
			out.println("</script>");
			return;
		}
	}
	
}