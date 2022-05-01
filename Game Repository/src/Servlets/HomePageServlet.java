package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import Models.User;

public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String user = (String) req.getSession().getAttribute("user");
		if (user == null) {
			System.out.println("User is not logged in");
			
			// user is not logged in, or the session expired
			resp.sendRedirect("../gamerepo/login");
			return;
		}
		
		HttpSession session = req.getSession(false);
		
		System.out.println("HeHeHe");
		if (req.getParameter("logout") != null) {
			req.getSession().removeAttribute("user");
			resp.sendRedirect("../gamerepo/login");
			
			return;
		}
		
		System.out.println("Home Servlet: doGet");
		req.getRequestDispatcher("_view/homepage.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User model = new User(null, null);
		
		String username = getString(resp, "username");
		model.setUsername(username);
		
		req.setAttribute("user", model);
		
		req.getRequestDispatcher("_view/homepage.jsp").forward(req, resp);
	}

	private String getString(HttpServletResponse req, String name) {
		return ((ServletRequest) req).getParameter(name);
		
	}
}