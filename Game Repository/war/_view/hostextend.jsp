<!DOCTYPE html>

<%! int i; %>

<%@page import="Models.Game" %>
<%@page import= "Database.elves.DatabaseProvider" %>
<%@page import= "Database.elves.DerbyDatabase" %>
<%@page import= "Database.elves.IDatabase" %>
<%@page import= "Database.elves.InitDatabase" %>
<%@ page import = "java.io.*,java.util.*" %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/hostextended.css"/>
        <title>Host Extended</title>
    </head>
    
    <body class=StaticBackground>
    	
    	<div class="HeaderStyle">${user}</div>
		<script>
			<%
			InitDatabase.init();
			IDatabase db = DatabaseProvider.getInstance();
			int gId = (int) session.getAttribute("gameId");
			Game game = db.getGameFromGameId(gId);
			if (game == null) {
				response.sendRedirect("../home");
			}
			
			switch (game.getGameKey()) {
			case IDatabase.Key_Blackjack: %>
				document.body.style.color = "black";
				document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

				localStorage.setItem("buttonValue", 'blackjack');
				console.log("buttonValue");
				<% break;
			case IDatabase.Key_Uno: %>
				document.body.style.color = "black";	
				document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
				localStorage.setItem("buttonValue", 'uno');
				console.log("buttonValue");
				<% break;
			case IDatabase.Key_UnoFlip: %>
				document.body.style.color = "white";
				document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
				localStorage.setItem("buttonValue", 'unoflip');
				console.log("buttonValue");
				<% break;
			case IDatabase.Key_ExplodingKittens: %>
				document.body.style.color = "white";
				document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
				localStorage.setItem("buttonValue", 'expoldingkittens');
				console.log("buttonValue");
				<% break;
			default: %>
				document.body.style.color = "white";
	  			document.body.style.backgroundImage = "url('_view/css/Back.png')";
	  			<% break;
			}
			%>
		 </script>

    	<div class="BackButton">
    		<a href="../gamerepo/home">
            	<button class="ButtonStyle" name="leave" type="submit">Disband Lobby</button>
            </a>
    	</div>
        <div class="AccountCreation" style="width: 60%; margin-bottom: -70px; margin-top: 20px;">
        	<span style="font-size: 300%; width: 60%; color: #ffffffff; background-color: #3b3b3bd8; border-radius: 13px; ">Game ID: <%out.print(game.getGameCode());%></span>
        </div>
       	<div style="width: 60%; margin-top: -80px; text-align: center; margin: auto;">
       		<%for (int p : game.getPlayerIds()) { %>
       			<p style="margin-left: auto; margin-right: auto; margin-bottom: -20px; font-size: 300%; width: 90%; color: #ffffffff; background-color: #3b3b3bd8; border-radius: 13px; "><% out.println(db.getNameFromPlayerId(p));%></p>
       		<%}%>
       	</div>
       	<script src="_view/callAjax.js"></script>
		<script>setTimeout(callAjax, 1000, <% out.print(game.getUpdate()); %>);</script>
    </body>
</html>