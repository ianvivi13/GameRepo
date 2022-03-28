package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class InstructionPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Instructions Servlet: doGet");
		
		req.getRequestDispatcher("_view/instructions.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setAttribute("username", "user");
		
		req.getRequestDispatcher("_view/instructions.jsp").forward(req, resp);
	}
}
