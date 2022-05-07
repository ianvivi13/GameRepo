<!-- The 4 Uno Colors T-->
<!-- Servlet TH--> 
<!--Fix JSP-->



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
            <%int gId = 1; %>//(int) session.getAttribute("gameId"); %>
            <%Game game = db.getGameFromGameId(gId); %>
            <%String us = (String) session.getAttribute("user"); %>
            <% Integer currentPlayerId = null;%>
            
            <% try { 
            	currentPlayerId = game.getTurnOrder().CurrentPlayer();
             } catch (Exception e) {} %>
        	
        </script>
        <div id="left"><a href="../gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button>  <!--fdstgsrth-->
        </a>
    	</div>
    	
    	<%Player playerLeft = null; %>
        <%Player playerRight = null; %>
        <%Player playerTop = null; %>
        <%Player playerBottom = null; %>
        <%try { %>
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
		    <% } else if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(3)) == db.getUserIDfromUsername(us)) { %>
		        <%playerBottom = game.getIndexPlayer(3);%>
			    <%playerRight = game.getIndexPlayer(0);%>
			    <%playerTop = game.getIndexPlayer(1);%>
			    <%playerLeft = game.getIndexPlayer(2);%>
		        <% } %>
	        <% } catch (Exception e) { %>
	        	response.sendRedirect("../home");
	        <% } %>
    	
    	<div class="container">
        	
    	<div class="top">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerTop))); %> </div>
        		
    		<div class="cards">
        		<% boolean firstFlag = true; %>
        		<% if (currentPlayerId == null) { firstFlag = false; } %>
            	<% for (Object o : playerTop.getPile().getPile()) { %>
	            	<% if (firstFlag) { %>
	            		<img id="piliTop" src="_view/images/UnoCards/back.png" >
	            		<% firstFlag = true; %>
	            	<% } else { %>
		            	<% UnoCard c = ((UnoCard) o); %>
		            	<% String pathTop = c.getImagePath(); %>
		            	<img id="piliTop" src="<%out.println(pathTop);%>">
	            	<% } %>
            	<% }%>
        	</div>
    	</div>

        <div class="left">
        	<div id="centersSides"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerLeft))); %> </div>
        	
        	<div class="cards">
	    		<% if (currentPlayerId == null) { firstFlag = false; } %>
	        	<% for (Object o : playerLeft.getPile().getPile()) { %>
	            	<% if (firstFlag) { %>
	            		<img id="piliLeft" src="_view/images/UnoCards/back.png" >
	            		<% firstFlag = true; %>
	            	<% } else { %>
		            	<% UnoCard c = ((UnoCard) o); %>
		            	<% String pathLeft = c.getImagePath(); %>
		            	<img id="piliLeft" src="<%out.println(pathLeft); %>">
	            	<% } %>
	        	<% }%>
	    	</div>
        </div>

        <div class="content">
        	<div class="split">
        		<div id="imgCenter" >
        		<form method="post">
  
        		<button type="submit" class="pili IFHY" name="Draw" value="Draw" > <img src="_view/images/UnoCards/back.png"></button>
        		
        		</div>
        	</div>
        	</form>
        	
        	<div class="split">
        		<div id="imgCenter" >
        			
        			<img class="deck" src="<%out.print(((UnoCard) game.getAltPile().getTopCard()).getImagePath());%>" style="width: 50%;">
        		
				</div>
        	</div>
        	
        </div>
        
        <div class="right">
        	<div id="centersSides"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerRight))); %> </div>
        	
        	<div >
	    		
	    		<% if (currentPlayerId == null) { firstFlag = false; } %>
	        	<% for (Object o : playerRight.getPile().getPile()) { %>
	            	<% if (firstFlag) { %>
	            		<img id="piliRight" src="_view/images/UnoCards/back.png" >
	            		<% firstFlag = true; %>
	            	<% } else { %>
		            	<% UnoCard c = ((UnoCard) o); %>
		            	<% String pathRight = c.getImagePath(); %>
		            	<img id="piliRight" src="<%out.println(pathRight);%>">
	            	<% } %>
	        	<% } %>
	        </div>
        </div>
        
        <div class="bottom">
        	<div id="centers"> <% out.print(db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerBottom))); %> </div>
        	
        	<div class="cards">
        	<% for (Object o : playerBottom.getPile().getPile()) { %>
				<% UnoCard c = ((UnoCard) o); %>
				<% String pathBottom = c.getImagePath(); %>
				<form method="post">
				<% if (c.getColor() != Color.BLACK) {%>
				
				<button type="submit" id="pili" name="playCard" value="playCard" style="width: 120%; height:100%; background: none; border: none;"> <img src="<%out.println(pathBottom);%>"></button>
				
				<% } else { %>
				<button type="submit" id="pili" name="playSpecialCard" value="playSpecialCard" style="width: 120%; height:100%; background: none; border: none;"> <img src="<%out.println(pathBottom);%>"></button>

				<% } %>
				
				<% } %>
				
				</form>
				</div>
        </div>
    </div>        
    
    	<div id="fourColors"></div

    <%/* if (currentPlayerId == null) { %>
	<% int q = game.getPlayerIds().get(0); %>
   	<% String s = db.getNameFromPlayerId(q); %>
   	<% boolean b = UnoController.checkWin(gId); %>
   	<% boolean z = s.equals(us); %>
   	<a href="../gamerepo/home">
   	<% if (b == z) { %>
   		<img id = "foreground" src="_view/images/Win.png">
   	<% } else { %>
   		<img id = "foreground" src="_view/images/Lose.png">
   	<% } %>
   	</a>
<% } */%>

<script src="_view/callAjax.js"></script>
<script>setTimeout(callAjax, 1000, <% out.print(game.getUpdate()); %>);</script>
 
    </body>
</html>