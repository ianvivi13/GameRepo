package Servlets;

import java.io.IOException;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.UnoController;
import Models.Game;

public class UnoPageServlet extends HttpServlet {
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

		System.out.println("Uno Servlet: doGet: " + user);
		
		req.getRequestDispatcher("_view/uno.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int gId = (int) req.getSession().getAttribute("gameId");
		
		try {
            if (req.getParameter("play") != null) {
            	UnoController.play(gId);
            } else if (req.getParameter("special") != null) {
            	UnoController.special(gId);
            }
            
        } catch (Exception e) {
            System.out.println("There is an error with: " + e);
        }

		resp.sendRedirect("../gamerepo/uno");
	}
}
