package Servlets;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Models.BlackJackController;
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
        
        
        // if play button was pressed :
        
        IDatabase db;
        db = DatabaseProvider.getInstance();
        int i = db.getUserIDfromUsername(user);
        
        int created;
        try {
        	Player player1 = new Player(true, i);
            created = db.createPlayer(player1);
        } catch (Exception PlayerAlreadyExistsException) {
            resp.sendRedirect("../gamerepo/home");
            return;
        }
        String m = "me"; // replace this with the correct req.get -------------------------------------------------------------
        Game model;
        /*
        switch (m) {
        	case "ExplodingKittens": 
        		model = new Game(IDatabase.Key_ExplodingKittens);
        		break;
        	case "Uno":
        		model = new Game(IDatabase.Key_Uno);
        		break;
        	case "UnoFlip":
        		model = new Game(IDatabase.Key_UnoFlip);
        		break;
        	default:
        		model = new Game(IDatabase.Key_Blackjack);
        }
        */
        model = new Game(IDatabase.Key_Blackjack);
        model.addPlayer(created);
        int newGame = db.createGame(model);
        req.getSession().setAttribute("gameId", newGame);
        resp.sendRedirect("../gamerepo/hostextend");
        return;
        // else
        
        
        
  
        
        //req.getRequestDispatcher("_view/host.jsp").forward(req, resp);
        // end if statement
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("_view/host.jsp").forward(req, resp);
    }
    
}