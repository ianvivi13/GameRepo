package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.Game;

public class JoinExtendedPageServlet extends HttpServlet {
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
		
		IDatabase db;
        db = DatabaseProvider.getInstance();
        int gId = (int) req.getSession().getAttribute("gameId");
        Game game = db.getGameFromGameId(gId);
		
        if (game.lobbyFull()) {
        	switch (game.getGameCode()) {
        		case IDatabase.Key_ExplodingKittens:
        			resp.sendRedirect("../gamerepo/home");
        		case IDatabase.Key_Uno:
        			resp.sendRedirect("../gamerepo/home");
        		case IDatabase.Key_UnoFlip:
        			resp.sendRedirect("../gamerepo/home");
        		default:
        			resp.sendRedirect("../gamerepo/blackjack");
        	}
        	
        }
		
		if (req.getParameter("leave") != null) {
			resp.sendRedirect("../gamerepo/home");
		}
		
		System.out.println("Join Extended Servlet: doGet: " + user);
		
		req.getRequestDispatcher("_view/joinextended.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("_view/joinextended.jsp").forward(req, resp);
	}
	
}