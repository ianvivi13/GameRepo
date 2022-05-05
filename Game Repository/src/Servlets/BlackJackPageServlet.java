package Servlets;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import Models.BlackJackController;

public class BlackJackPageServlet extends HttpServlet {
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

		System.out.println("BlackJack Servlet: doGet: " + user);
		req.getRequestDispatcher("_view/blackjack.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int gId = (int) req.getSession().getAttribute("gameId");
        
        try {
            if (req.getParameter("Hit") != null) {
            	BlackJackController.hit(gId);
            } else if (req.getParameter("Hold") != null) {
            	BlackJackController.hold(gId);
            } else if (req.getParameter("Freeze") != null) {
            	BlackJackController.freeze(gId);
            }
        } catch (Exception e) {
            System.out.println("There is an error with: " + e);
        }

		resp.sendRedirect("../gamerepo/blackjack");
	}
}
