package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.Game;
import Models.Player;

public class JoinPageServlet extends HttpServlet {
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
		
		System.out.println("Join Servlet: doGet");
		
		IDatabase db;
        db = DatabaseProvider.getInstance();
        int i = db.getUserIDfromUsername(user);
        
		String gameCode = req.getParameter("GameId");
		if (gameCode != null) {
			System.out.println(gameCode);
			
			int g = db.gameCodeValid(gameCode);
			if (g > 0) {
				try {
					Player player = new Player(true, i);
					int p = db.createPlayer(player);
					Game d = db.getGameFromGameId(g);
					d.addPlayer(p);
					db.updateGame(g, d);
					req.getSession().setAttribute("gameId", g);
		            resp.sendRedirect("../gamerepo/joinextended");
				} catch (Exception PlayerAlreadyExistsException) {
		        	resp.sendRedirect("../gamerepo/home");
		        	return;
		        }
			} else {
				resp.sendRedirect("../gamerepo/join");
			}
			
		}
		
		req.getRequestDispatcher("_view/join.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("_view/homepage.jsp").forward(req, resp);
	}
	
}