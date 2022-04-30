<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/blackjack.css"/>

        <%@page import="Models.StandardCard" %>
        <%@page import="Models.Pile" %>
        <%@page import="Models.Game" %>
        <%@page import="Models.BlackJackController" %>
        <%@page import="Models.Player" %>
        
        <%@page import= "Database.elves.DatabaseProvider" %>
        <%@page import= " Database.elves.DerbyDatabase" %>
        <%@page import= "Database.elves.IDatabase" %>
        <%@page import= "Database.elves.InitDatabase" %>
        
        <title>
            BlackJack
        </title>
    </head>
    <body class="BlackJack">
        <script>
            document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
            document.body.style.color = "black";
            
            <%InitDatabase.init(); %>
            <%IDatabase db = DatabaseProvider.getInstance(); %>
            <%Game game = db.getGameFromGameId(1); %>
            
        </script>
        <div id="left"><a href="http://localhost:8080/gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button> 
        </a>
    </div>
    <div id="deck">
	<div id="imgCenter">
    <% for (int i = 0; i < game.getMainPile().getNumCards(); i++) { %>
    	<img id="pili" src="_view/images/StandardCards/back-sm.png">
  <%  }
    	%>
    	</div>
    
    </div>
    <%Player playerLeft = game.getIndexPlayer(0);%>
    <%Player playerRight = game.getIndexPlayer(1);%>
    
    <div id="players">
        <div class="split" id="player1">
        
        	<div id="centers">Main Hand: ${user}</div>
                
                <div class="cards">  
	                <% for (Object o : playerLeft.getPile().getPile()) { %>
	                <% StandardCard c = ((StandardCard) o); %>
	                <% String pathLeft = c.getImagePath(); %>
	            	<img id="pili" src="<%out.println(pathLeft);%>">
	               <%  
	               }
	                %>
                </div>
                
                <form>
                <button class="ButtonStyle" id="Hit" name="Hit" type="submit" onClick="hit(game)" value="Hit">Hit</button> 
                
            </form>         
            
            <form class="center">
                <button class="ButtonStyle" id="blend" name="Hold" type="submit" onClick="hold(game)" value="Hold">Hold</button> 
                <button class="ButtonStyle" id="blend" name="Freeze" type="submit" onClick="freeze(game)" value="Freeze">Freeze</button> 
                
            </form>
            
        </div>
        <div class="split" id="player2">
        	
        	<div><% out.println(playerRight); %> </div>
        	
	        	<div class="cards">  
	            	<% for (Object o : playerRight.getPile().getPile()) { %>
	            	<% StandardCard c = ((StandardCard) o); %>
	            	<% String pathRight = c.getImagePath(); %>
	            	<img id="pili" src="<%out.println(pathRight);%>">
	            	<%  
	            		}
	            	%>
	            	</div>
        	
            </div>
        	</div>
        </div>
    </body>

</html>