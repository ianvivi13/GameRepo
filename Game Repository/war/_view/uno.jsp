<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/uno.css"/>

        <%@page import="Models.StandardCard" %>
        <%@page import="Models.Pile" %>
        <%@page import="Models.Game" %>
        <%@page import="Models.UnoController" %>
        <%@page import="Models.Player" %>
        <%@page import="Models.Value" %>
        <%@page import="Models.Color" %>
        
        <%@page import="Models.StatisticsUno" %>
        
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
    		 	<% for (Object o : playerTop.getPile().getPile()) { %>
    		 		<% StandardCard c = ((StandardCard) o); %>
    		 		<% String pathTop = c.getImagePath(); %>
    		 		<form method="post">
    		 		<button id="pili" src="<%out.println(pathTop);%>">
    		 		</form>
    		 		<% } %>
        	</div>

        <div class="left">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerLeft))); %> </div>
    		<% for (Object o : playerLeft.getPile().getPile()) { %>
    			<% StandardCard c = ((StandardCard) o); %>
    			<% String pathLeft = c.getImagePath(); %>
    			<form method="post">
    			<button id="pili" src="<%out.println(pathLeft);%>">
    			</form>
    			<% } %>
        </div>

        <div class="content">
        	<div id="imgCenter">
        		<img id="pili" src="_view/images/UnoCards/back.png" style="width: 12%;">
    		</div>
            
        </div>

        <div class="right">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerRight))); %> </div>
        	<% for (Object o : playerRight.getPile().getPile()) { %>
				<% StandardCard c = ((StandardCard) o); %>
				<% String pathRight = c.getImagePath(); %>
				<form method="post">
				<button id="pili" src="<%out.println(pathRight);%>">
				</form>
				<% } %>
        </div>
        
        <div class="bottom">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerBottom))); %> </div>
        	<% for (Object o : playerBottom.getPile().getPile()) { %>
				<% StandardCard c = ((StandardCard) o); %>
				<% String pathBottom = c.getImagePath(); %>
				<form method="post">
				<button id="pili" src="<%out.println(pathBottom);%>">
				</form>
				<% } %>
        </div>
    </div>           

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        
       
    </body>

</html>