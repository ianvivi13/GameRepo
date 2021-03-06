package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

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
		String username = req.getParameter("Username");
		String password = req.getParameter("Password");
		
		System.out.println("Attempting to create new user: " + username);
		
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		try {
			db.createUser(username, password);
			System.out.println("User created succesfully: " + username);
			resp.sendRedirect("../gamerepo/login");
		} catch (Exception e) {
			System.out.println("User could not be created - possible duplicate username: " + username);
			resp.sendRedirect("../gamerepo/new");
			
			PrintWriter out = resp.getWriter(); 
			out.println("<script type=\"text/javascript\">"); 
			out.println("alert('Username already exist');"); 
			out.println("location=../gamerepo/new"); 
			out.println("</script>");
		} 
	}
	
}