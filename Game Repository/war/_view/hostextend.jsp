<!DOCTYPE html>

<%! int i; %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/hostextended.css"/>
        <title>Host Extended</title>
    </head>
    
    <body class=StaticBackground>
    	<div class="HeaderStyle">
    		<a href="http://localhost:8080/gamerepo/hostextend"><button class="ButtonStyle" type="submit">Host</button>
    	</div>
    	<div class="BackButton">
    		<a href="http://localhost:8080/gamerepo/home">
            	<button class="ButtonStyle" type="submit">Disband Lobby</button>
            </a>
    	</div>
        <div class="AccountCreation">
        	<div class="title">Game ID: XXXX-YYYYY</div>
        </div>
       	<div class="leftside">
       		<%for ( i = 1; i <= 9; i++){ %>
       			<div class="player">
       				<div class="stat">
       					<% out.println(i); %>
       				</div>
       				<div class="usertitle">
       					This is my master title
       				</div>
       				<div class="name">
       					MMMMMMMMMMMMMMMMMMMM
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