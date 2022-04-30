<!DOCTYPE html>

<%! int i; %>

<%@page import="Models.Game" %>
<%@page import= "Database.elves.DatabaseProvider" %>
<%@page import= " Database.elves.DerbyDatabase" %>
<%@page import= "Database.elves.IDatabase" %>
<%@page import= "Database.elves.InitDatabase" %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/hostextended.css"/>
        <title>Host Extended</title>
    </head>
    
    <body class=StaticBackground>
		<script>
			let value = localStorage.getItem("buttonValue");
				console.log(value);
	
					switch (value) {
					case 'blackjack':
						
						document.body.style.color = "black";
						document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
	
						localStorage.setItem("buttonValue", 'blackjack');
						console.log("buttonValue");
						break;
		
					case 'uno':

						document.body.style.color = "black";	
						document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
						localStorage.setItem("buttonValue", 'uno');
						console.log("buttonValue");
						break;    
		
					case 'unoflip':
						  
						document.body.style.color = "white";
						document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
						localStorage.setItem("buttonValue", 'unoflip');
						console.log("buttonValue");
						break;
		
					case 'expoldingkittens':
		
						document.body.style.color = "white";
						document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
						localStorage.setItem("buttonValue", 'expoldingkittens');
						console.log("buttonValue");
						break;
	
					default:
						document.body.style.color = "white";
			  			document.body.style.backgroundImage = "url('_view/css/Back.png')";
					}
					<%InitDatabase.init(); %>
					<%IDatabase db = DatabaseProvider.getInstance(); %>
					<%int gId = (int) session.getAttribute("gameId"); %>
					<%Game game = db.getGameFromGameId(gId); %>
				    
					function timeRefresh(time) {
			        	setTimeout("location.reload(false);", time);
			      	} 
			    	timeRefresh(1000)
					
		  </script>

    	<div class="HeaderStyle">
    		<a href="../gamerepo/hostextend"><button class="ButtonStyle" id="game" type="submit">Host</button></a>
    	</div>
    	<div class="BackButton">
    		<a href="../gamerepo/home">
            	<button class="ButtonStyle" type="submit">Disband Lobby</button>
            </a>
    	</div>
        <div class="AccountCreation">
        	
        	<div class="title">Game ID: <%out.print(game.getGameCode());%></div>
        </div>
       	<div class="leftside">
       		<%for (int p : game.getPlayerIds()) { %>
       			<div class="player">
       				<div class="stat">
       					<% out.println(p); %>
       				</div>
       				<div class="usertitle">
       					This is my master title
       				</div>
       				<div class="name">
       					<% out.println(db.getNameFromPlayerId(p)); %>
       				</div>
       			</div>
       		<%}%>
       	</div>
       	<div class="rightside">
       		<ul class="rules">
       			<%for ( i = 1; i <= 20; i++){ %>
	       			<li> <% out.println("Item: " + i); %> </li>
       			<%}%>
       		</ul>
       	</div>
    </body>
</html>