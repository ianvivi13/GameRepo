package Servlets;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.BlackJackController;
import Models.Game;
import Models.UnoController;


public class HostPageExtendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = (String) req.getSession().getAttribute("user");
		if (user == null) {
			System.out.println("User is not logged in");
			// user is not logged in, or the session expired
			resp.sendRedirect("../gamerepo/login");
			return;
		}
		System.out.println("Host Extended Servlet: doGet: " + user);
		IDatabase db;
        db = DatabaseProvider.getInstance();
        int gId = (int) req.getSession().getAttribute("gameId");
        Game game = db.getGameFromGameId(gId);
        try {
	        if (game.lobbyFull()) {
	        	switch (game.getGameKey()) {
	        		case IDatabase.Key_ExplodingKittens:
	        			resp.sendRedirect("../gamerepo/home");
	        			return;
	        		case IDatabase.Key_Uno:
	        			if (db.getNameFromPlayerId(game.getPlayerIds().get(0)).equals(user)) {
	        				UnoController.initialize(gId);
	        			}
	        			resp.sendRedirect("../gamerepo/uno");
	        			return;
	        		case IDatabase.Key_UnoFlip:
	        			resp.sendRedirect("../gamerepo/home");
	        			return;
	        		default:
	        			if (db.getNameFromPlayerId(game.getPlayerIds().get(0)).equals(user)) {
	        				BlackJackController.initialize(gId);
	        			}
	        			resp.sendRedirect("../gamerepo/blackjack");
	        			return;
	        	}
	        }
		} catch (Exception e) {
			resp.sendRedirect("../gamerepo/home");
			return;
		}
		if (req.getParameter("leave") != null) {
			resp.sendRedirect("../gamerepo/home");
			return;
		}
		req.getRequestDispatcher("_view/hostextend.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("_view/hostextend.jsp").forward(req, resp);
	}
	
}