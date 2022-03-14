package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class StatisticsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Statistics Servlet: doGet");
		
		req.getRequestDispatcher("_view/statpage.jsp").forward(req, resp);
	}
	
}