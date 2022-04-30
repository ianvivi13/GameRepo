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
            <%int gId = (int) session.getAttribute("gameId"); %>
            <%Game game = db.getGameFromGameId(gId); %>
            <%String us = (String) session.getAttribute("user"); %>
            <%int currentPlayerId = game.getTurnOrder().CurrentPlayer(); %>

            function timeRefresh(time) {
            	setTimeout("location.reload(false);", time);
          	} 
        	timeRefresh(1000)
        </script>
        <div id="left"><a href="http://localhost:8080/gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button> 
        </a>
    </div>
    <div id="deck">
	<div id="imgCenter">
    <% for (int i = 0; i < 1; i++) { %>
    	<img id="pili" src="_view/images/StandardCards/back-sm.png">
  <%  }
    	%>
    	</div>
    
    </div>
    <%Player playerLeft = null; %>
    <%Player playerRight = null; %>
    <%if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(0)) == db.getUserIDfromUsername(us)) { %>
    <%playerLeft = game.getIndexPlayer(0);%>
    <%playerRight = game.getIndexPlayer(1);%>
    <% } else { %>
    <%playerRight = game.getIndexPlayer(0);%>
    <%playerLeft = game.getIndexPlayer(1);%>
    <% } %>
    
    <div id="players">
        <div class="split" id="player1">
        	
        	<div id="centers">${user}</div>
                
                <div class="cards">  
	                <% for (Object o : playerLeft.getPile().getPile()) { %>
	                <% StandardCard c = ((StandardCard) o); %>
	                <% String pathLeft = c.getImagePath(); %>
	            	<img id="pili" src="<%out.println(pathLeft);%>">
	               <%  
	               }
	                %>
                </div>
            
            <% if (currentPlayerId == db.getPlayerIdFromPlayer(playerLeft)) { %>
            <form class="center" id="Hit" method="post">
            	<button class="ButtonStyle" id="blend" name="Hit" type="submit" onClick="hit(game)" value="Hit">Hit</button> 
                <button class="ButtonStyle" id="blend" name="Hold" type="submit" onClick="hold(game)" value="Hold">Hold</button> 
                <button class="ButtonStyle" id="blend" name="Freeze" type="submit" onClick="freeze(game)" value="Freeze">Freeze</button> 
            </form>
            <% } %>
            
        </div>
        <div class="split" id="player2">
        	
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerRight))); %> </div>
        	
	        	<div class="cards">
	        		<% boolean firstFlag = true; %>
	            	<% for (Object o : playerRight.getPile().getPile()) { %>
		            	<% if (firstFlag) { %>
		            		<img id="pili" src="_view/images/StandardCards/back-sm.png">
		            		<% firstFlag = false; %>
		            	<% } else { %>
			            	<% StandardCard c = ((StandardCard) o); %>
			            	<% String pathRight = c.getImagePath(); %>
			            	<img id="pili" src="<%out.println(pathRight);%>">
		            	<% } %>
	            	<% }%>
	            </div>
            </div>
        </div>
    </body>

</html>