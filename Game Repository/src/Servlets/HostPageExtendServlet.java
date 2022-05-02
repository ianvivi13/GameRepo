package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.BlackJackController;
import Models.Game;


public class HostPageExtendServlet extends HttpServlet {
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
		
		System.out.println("Host Extended Servlet: doGet: " + user);
		
		IDatabase db;
        db = DatabaseProvider.getInstance();
        int gId = (int) req.getSession().getAttribute("gameId");
        Game game = db.getGameFromGameId(gId);
		
        try {
	        if (game.lobbyFull()) {
	        	switch (game.getGameCode()) {
	        		case IDatabase.Key_ExplodingKittens:
	        			resp.sendRedirect("../gamerepo/home");
	        		case IDatabase.Key_Uno:
	        			resp.sendRedirect("../gamerepo/home");
	        		case IDatabase.Key_UnoFlip:
	        			resp.sendRedirect("../gamerepo/home");
	        		default:
	        			BlackJackController.initialize(gId);
	        			resp.sendRedirect("../gamerepo/blackjack");
	        	}
	        	
	        }
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (req.getParameter("leave") != null) {
			
			//Will delete the game and relocate the host... In join extended, if there no longer exists a game with the same id, exit to home
			resp.sendRedirect("../gamerepo/home");
		}
		
		req.getRequestDispatcher("_view/hostextend.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		req.getRequestDispatcher("_view/hostextend.jsp").forward(req, resp);
	}
	
}