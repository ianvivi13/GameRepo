package Servlets;

import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.elves.DatabaseProvider;
import Database.elves.IDatabase;
import Database.elves.InitDatabase;

public class GameAjaxServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
        
    public GameAjaxServlet() {
        super();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String user = (String) request.getSession().getAttribute("user");
        if (user == null) {
            System.out.println("User is not logged in");

            // user is not logged in, or the session expired
            response.sendRedirect("../gamerepo/login");
            return;
        }
    	
    	InitDatabase.init();
		IDatabase db = DatabaseProvider.getInstance();
    	int gId = (int) request.getSession().getAttribute("gameId");

    	int CurrentUpdate = -1;
        try {
            CurrentUpdate = db.getUpdateCountFromGameId(gId);
        } catch (Exception e) {
            CurrentUpdate = -1;
        }
        
    	String CU = Integer.toString(CurrentUpdate);
        response.getWriter().write(CU);
        return;
    	
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}