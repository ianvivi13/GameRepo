package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
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
		
		System.out.println("Join Servlet: doGet");
		
		IDatabase db;
        db = DatabaseProvider.getInstance();
        int gId = (int) req.getSession().getAttribute("gameId");
        Game game = db.getGameFromGameId(gId);
		
		System.out.println("GC: " + game.getGameCode());
		
		req.getRequestDispatcher("_view/hostextend.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		req.getRequestDispatcher("_view/hostextend.jsp").forward(req, resp);
	}
	
}