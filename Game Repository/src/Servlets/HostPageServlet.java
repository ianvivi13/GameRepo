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
        System.out.println("Host Servlet: doGet");
        
        
        IDatabase db;
        db = DatabaseProvider.getInstance();
        int i = db.getUserIDfromUsername(user);
        System.out.println("UserId:" + i);
        try {
            
            Player player1 = new Player(true, i);
            
            int created = db.createPlayer(player1);
            
            Game model = new Game("BLJ");
            model.addPlayer(created);

            int newGame = db.createGame(model);
            
            req.getSession().setAttribute("gameId", newGame);
            resp.sendRedirect("../gamerepo/hostextend");
            return;   

        } catch (Exception PlayerAlreadyExistsException) {
            resp.sendRedirect("../gamerepo/home");
            return;
        }
            
        
       
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("_view/host.jsp").forward(req, resp);
    }
    
}