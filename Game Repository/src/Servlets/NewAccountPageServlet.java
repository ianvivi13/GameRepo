package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;
import Database.elves.UserExistsException;
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
		String username = req.getParameter("Username");
		String password = req.getParameter("Password");
		
		System.out.println("Verify User");
		System.out.println(username);
		System.out.println(password);
		
		InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
		try {
			db.createUser(username, password);
			resp.sendRedirect("http://localhost:8080/gamerepo/login");
		} catch (UserExistsException e) {
			System.out.println("Woops you're a dumb");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}