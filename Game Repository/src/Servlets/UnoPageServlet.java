package Servlets;

import java.io.IOException;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.UnoController;
import Models.Color;
import Models.Game;
import Models.UnoCard;

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
		try {
			req.getRequestDispatcher("_view/uno.jsp").forward(req, resp);
		} catch (Exception e) {
			System.out.println("uh oh: " + e);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int gId = (int) req.getSession().getAttribute("gameId");

		try {
            if (req.getParameter("playCard") != null) {
            	UnoCard card = UnoCard.fromString((String) req.getParameter("playCard"));
            	UnoController.playCard(gId, card);
            } else if (req.getParameter("Draw") != null) {
            	UnoController.drawCardOrRecycleWaste(gId, 1, true);
            } else if ((req.getParameter("card") != null) && (req.getParameter("color") != null)) {
            	UnoCard card = UnoCard.fromString((String) req.getParameter("card"));
            	UnoController.playSpecialCard(gId, card, req.getParameter("color"));
            }
            
        } catch (Exception e) {
            System.out.println("There is an error with: " + e);
        }

		resp.sendRedirect("../gamerepo/uno");
	}
}
