<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import = "java.io.*,java.util.*" %>

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
            <%  
            InitDatabase.init();
            IDatabase db = DatabaseProvider.getInstance();
            int gId = (int) session.getAttribute("gameId");
            Game game = db.getGameFromGameId(gId);
            String us = (String) session.getAttribute("user");
            Integer currentPlayerId = null;
             try { 
            	currentPlayerId = game.getTurnOrder().CurrentPlayer();
             } catch (Exception e) {}
        	%>
        </script>
        <div id="left"><a href="../gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button> 
        </a>
    	</div>
		<div id="imgCenter">
	    	<img id="pili" src="_view/images/StandardCards/back-sm.png" style="width: 12%;">
	    </div>
	<%
    Player playerLeft = null;
    Player playerRight = null;
    try {
	    if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(0)) == db.getUserIDfromUsername(us)) {
		    playerLeft = game.getIndexPlayer(0);
		    playerRight = game.getIndexPlayer(1);
	    } else {
		    playerRight = game.getIndexPlayer(0);
	    	playerLeft = game.getIndexPlayer(1);
	    }
    } catch (Exception e) {
    	response.sendRedirect("../home");
    }
    %>
    
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
            <% try { %>
	            <% if (currentPlayerId == db.getPlayerIdFromPlayer(playerLeft)) { %>
	            <form class="center" id="Hit" method="post">
	            	<button class="ButtonStyle" id="blend" name="Hit" type="submit" onClick="hit(game)" value="Hit">Hit</button> 
	                <button class="ButtonStyle" id="blend" name="Hold" type="submit" onClick="hold(game)" value="Hold">Hold</button> 
	                <button class="ButtonStyle" id="blend" name="Freeze" type="submit" onClick="freeze(game)" value="Freeze">Freeze</button> 
	            </form>
	            <% } %>
            <% } catch (Exception e) {}%>
            
        </div>
        <div class="split" id="player2">
        	
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerRight))); %> </div>
        	
	        	<div class="cards">
	        		<% boolean firstFlag = true; %>
	        		<% if (currentPlayerId == null) { firstFlag = false; } %>
	            	<% for (Object o : playerRight.getPile().getPile()) { %>
		            	<% if (firstFlag) { %>
		            		<img id="pili" src="_view/images/StandardCards/back-sm.png" >
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
        
        <% if (currentPlayerId == null) { %>
        	<% int q = game.getPlayerIds().get(0); %>
           	<% String s = db.getNameFromPlayerId(q); %>
           	<% boolean b = BlackJackController.checkWin(gId); %>
           	<% boolean z = s.equals(us); %>
           	<a href="../gamerepo/home">
           	<% if (b == z) {%>
           		<img id = "foreground" src="_view/images/Win.png">
           	<% } else { %>
           		<img id = "foreground" src="_view/images/Lose.png">
           	<% } %>
           	</a>
        <% } %>
    	<script src="_view/callAjax.js"></script>
		<script>setTimeout(callAjax, 1000, <% out.print(game.getUpdate()); %>);</script>
    </body>
</html>