<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/uno.css"/>

        <%@page import="Models.UnoCard" %>
        <%@page import="Models.Pile" %>
        <%@page import="Models.Game" %>
        <%@page import="Models.UnoController" %>
        <%@page import="Models.Player" %>
        <%@page import="Models.Value" %>
        <%@page import="Models.Color" %>
        
        <%@page import= "Database.elves.DatabaseProvider" %>
        <%@page import= " Database.elves.DerbyDatabase" %>
        <%@page import= "Database.elves.IDatabase" %>
        <%@page import= "Database.elves.InitDatabase" %>
        
        <title>
            Uno
        </title>
        
    </head>
    <body class="Uno">
        <script>
            document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
            document.body.style.color = "black";
            
            <%InitDatabase.init(); %>
            <%IDatabase db = DatabaseProvider.getInstance(); %>
            <%int gId = (int) session.getAttribute("gameId"); %>
            <%Game game = db.getGameFromGameId(gId); %>
            <%String us = (String) session.getAttribute("user"); %>
            <% Integer currentPlayerId = null;%>
        	
        </script>
        <div id="left"><a href="../gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button> 
        </a>
    	</div>
    	
    	<%Player playerLeft = null; %>
        <%Player playerRight = null; %>
        <%Player playerTop = null; %>
        <%Player playerBottom = null; %>
        <%if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(0)) == db.getUserIDfromUsername(us)) { %>
    	    <%playerBottom = game.getIndexPlayer(0);%>
    	    <%playerRight = game.getIndexPlayer(1);%>
    	    <%playerTop = game.getIndexPlayer(2);%>
    	    <%playerLeft = game.getIndexPlayer(3);%>
        <% } else if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(1)) == db.getUserIDfromUsername(us)) { %>
        	<%playerBottom = game.getIndexPlayer(1);%>
        	<%playerRight = game.getIndexPlayer(2);%>
        	<%playerTop = game.getIndexPlayer(3);%>
        	<%playerLeft = game.getIndexPlayer(0);%>
        <% } else if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(2)) == db.getUserIDfromUsername(us)) { %>
        	<%playerBottom = game.getIndexPlayer(2);%>
        	<%playerRight = game.getIndexPlayer(3);%>
        	<%playerTop = game.getIndexPlayer(0);%>
        	<%playerLeft = game.getIndexPlayer(1);%>
	    <% } else if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(3)) == db.getUserIDfromUsername(us)) %>
	        <%playerBottom = game.getIndexPlayer(3);%>
		    <%playerRight = game.getIndexPlayer(0);%>
		    <%playerTop = game.getIndexPlayer(1);%>
		    <%playerLeft = game.getIndexPlayer(2);%>
	        <% } %>
    	
    	<div class="container">
        	<div class="top">
        		<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerTop))); %> </div>
        		
        		<div class="cards">
	        		<% boolean firstFlag = true; %>
	        		<% if (currentPlayerId == null) { firstFlag = false; } %>
	            	<% for (Object o : playerTop.getPile().getPile()) { %>
		            	<% if (firstFlag) { %>
		            		<button id="pili" src="_view/images/UnoCards/back.png" >
		            		<% firstFlag = false; %>
		            	<% } else { %>
			            	<% UnoCard c = ((UnoCard) o); %>
			            	<% String pathTop = c.getImagePath(); %>
			            	<img id="pili" src="<%out.println(pathTop);%>">
		            	<% } %>
	            	<% }%>
	        	</div>
        	</div>

        <div class="left">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerLeft))); %> </div>
        	<div class="cards">
	    		<% boolean firstFlag = true; %>
	    		<% if (currentPlayerId == null) { firstFlag = false; } %>
	        	<% for (Object o : playerLeft.getPile().getPile()) { %>
	            	<% if (firstFlag) { %>
	            		<button id="pili" src="_view/images/UnoCards/back.png" >
	            		<% firstFlag = false; %>
	            	<% } else { %>
		            	<% UnoCard c = ((UnoCard) o); %>
		            	<% String pathLeft = c.getImagePath(); %>
		            	<img id="pili" src="<%out.println(pathLeft);%>">
	            	<% } %>
	        	<% }%>
	    	</div>
        </div>

        <div class="content">
        	<div id="imgCenter">
        		<img id="pili" src="_view/images/UnoCards/back.png" style="width: 12%;">
    		</div>
            
        </div>

        <div class="right">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerRight))); %> </div>
        	
        	<div class="cards">
	    		<% boolean firstFlag = true; %>
	    		<% if (currentPlayerId == null) { firstFlag = false; } %>
	        	<% for (Object o : playerRight.getPile().getPile()) { %>
	            	<% if (firstFlag) { %>
	            		<button id="pili" src="_view/images/UnoCards/back.png" >
	            		<% firstFlag = false; %>
	            	<% } else { %>
		            	<% UnoCard c = ((UnoCard) o); %>
		            	<% String pathRight = c.getImagePath(); %>
		            	<img id="pili" src="<%out.println(pathRight);%>">
	            	<% } %>
	        	<% } %>
	        </div>
        </div>
        
        <div class="bottom">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerBottom))); %> </div>
        	<% for (Object o : playerBottom.getPile().getPile()) { %>
				<% UnoCard c = ((UnoCard) o); %>
				<% String pathBottom = c.getImagePath(); %>
				<form method="post">
				<% if (playerBottom.getPile().getCard() == Color.Black /*Checking...*/) {%>
				<button id="pili" src="<%out.println(pathBottom);%>">
				<%} else { %>
				<button id="pili" src="<%out.println(pathBottom);%>">
				<% } %>
				</form>
				<% } %>
        </div>
    </div>           

    <% if (currentPlayerId == null) { %>
	<% int q = game.getPlayerIds().get(0); %>
   	<% String s = db.getNameFromPlayerId(q); %>
   	<% boolean b = UnoController.checkWin(gId); %>
   	<% boolean z = s.equals(us); %>
   	<a href="../gamerepo/home">
   	<% if (b == z) {%>
   		<img id = "foreground" src="_view/images/Win.png">
   	<% } else { %>
   		<img id = "foreground" src="_view/images/Lose.png">
   	<% } %>
   	</a>
<% } %>
 
    </body>
</html>