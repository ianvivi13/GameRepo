package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class JoinExtendedPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Join Extended Servlet: doGet");
		
		req.getRequestDispatcher("_view/joinextended.jsp").forward(req, resp);
	}
	
}