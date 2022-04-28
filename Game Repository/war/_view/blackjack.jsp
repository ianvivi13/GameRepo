<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/blackjack.css"/>

        <%@page import="Models.StandardCard" %>
        <%@page import="Models.BotNameGenerator" %>
        <%@page import="Models.Pile" %>
        <%@page import="Models.Game" %>
        
        <title>
            BlackJack
        </title>
    </head>
    <body class="BlackJack">
        <script>
            document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
            document.body.style.color = "black";
            
            <%BotNameGenerator botName = new BotNameGenerator(); %>
        </script>
        <div id="left"><a href="http://localhost:8080/gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button> 
        </a>
    </div>
    <div id="deck">
	<div id="imgCenter">
    <% for (int i = 0; i < 52; i++) { %>
    	<img id="pili" src="_view/images/StandardCards/back-sm.png">
  <%  }
    	%>
    	</div>
    
    </div>
    <div id="players">
        <div class="split" id="player1">
                <div id="centers">Main Hand: ${user}</div>
                
                //User user = getAttribue
                <% if (playOne == user) {
                		
                	//DISPLAY CARDS EXPOSED IF THIS IS PLAYER 1... Only the top card exposed
                   } else {
                	   //Display the cards unexposed
                   }
                	
                	%>
                
                //DISPLAY CARDS HERE
                
                //If player 2 does not display the retrieved attribute of the second player, do not display
                <% if (playOne == user) { %> //---------------------------------
                <form>
                <button class="ButtonStyle" id="Hit" type="submit" onClick="hit(game)" value="Hit">Hit</button> 
            </form>         
            
            <form class="center">
                <button class="ButtonStyle" id="blend" type="submit" onClick="hold(game)" value="Hold">Hold</button> 
                <button class="ButtonStyle" id="blend" type="submit" onClick="freeze(game)" value="Freeze">Freeze</button> 
            </form>
            
            	<% } %>
            
        </div>
        <div class="split" id="player2">
        	//Should be retrieved from the db
        	<div id="centers">Main Hand: <% out.println(botName.GenerateName()); %> </div>
            
        	//User user = getAttribue
            <% if (playOne == user) {
            		
            	//DISPLAY CARDS EXPOSED IF THIS IS PLAYER 1... Only the top card exposed
               } else {
            	   //Display the cards unexposed
               }
            	
            	%>
        	
            	//If player 2 does not display the retrieved attribute of the second player, do not display
        		<% if ( ) {%>
        		<form>
            		<button class="ButtonStyle" id="Hit" type="submit" onClick="hit(game)" value="Hit">Hit</button> 
            	</form>
        
        		<form class="center">
            		<button class="ButtonStyle" id="blend" type="submit" onClick="hold(game)" value="Hold">Hold</button> 
            		<button class="ButtonStyle" id="blend" type="submit" onClick="freeze(game)" value="Freeze">Freeze</button> 
            	</form>
        
        <% } %>
      
        	</div>
        </div>
    </body>

</html>