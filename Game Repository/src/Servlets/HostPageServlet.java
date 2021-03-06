package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.Game;
import Models.Player;

public class HostPageServlet extends HttpServlet {
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
        System.out.println("Host Servlet: doGet: " + user);

	    req.getRequestDispatcher("_view/host.jsp").forward(req, resp);
	        
	      
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
    	IDatabase db;
    	db = DatabaseProvider.getInstance();
	    
        String m = (String) req.getSession().getAttribute("happy");
        Game model;
        int players = Integer.parseInt((String) req.getParameter("MaxP"));
        
        if (m != null) {
	        switch (m) {
	        	case "expoldingkittens": 
	        		model = new Game(IDatabase.Key_ExplodingKittens);
	        		break;
	        	case "uno":
	        		model = new Game(IDatabase.Key_Uno);
	        		String com = (String) req.getParameter("communism");
	        	    String uti = (String) req.getParameter("utilitarianism");
	        	    
	        		// not special: 0
	        	    // communism only: 1
	        	    // uti only : 2
	        	    // both : 3
	        	    int aux = 0;
	        	    if (com != null) {
		        	    if (com.equals("on")) {
		        			aux += 1;
		        		}
	        	    }
	        	    if (uti != null) {
						if (uti.equals("on")) {
							aux += 2;  			
						}
	        	    }
					model.setAuxInt(aux);
	        		break;
	        	case "unoflip":
	        		model = new Game(IDatabase.Key_UnoFlip);
	        		break;
	        	default:
	        		model = new Game(IDatabase.Key_Blackjack);
	        }
	        model.setMaxPlayers(players);
	        String user = (String) req.getSession().getAttribute("user");
	        int i = db.getUserIDfromUsername(user);
	        int created;
	        try {
	            Player player1 = new Player(true, i);
	            created = db.createPlayer(player1);
	        } catch (Exception PlayerAlreadyExistsException) {
	            resp.sendRedirect("../gamerepo/home");
	            return;
	        }
	        model.addPlayer(created);
	        int newGame = db.createGame(model);
	        req.getSession().setAttribute("gameId", newGame);
	        resp.sendRedirect("../gamerepo/hostextend");
	        return;
        } else {
        	resp.sendRedirect("../gamerepo/multiplayer");
        	return;
        }
    
    	//String m = (String) req.getSession().getAttribute("happy");
        	//resp.sendRedirect("../gamerepo/multiplayer");
         
        	//req.getRequestDispatcher("_view/host.jsp").forward(req, resp);
        
    }
    
}