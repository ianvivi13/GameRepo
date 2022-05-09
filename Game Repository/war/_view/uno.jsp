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
    <body class="Uno" style="overflow: hidden; height: 98%; box-shadow: inset 0 0 0 50vw rgba(0,0,0,0.3);">
    	<audio id="audioplayer" loop="false" autoplay="true" preload="auto" src="_view/background.mp3"></audio>
    
    	<script src="_view/audio.js"></script>
    
    
        <script>
            document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
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
        <div id="left" style="margin-bottom: -3%;  z-index: 1000; position: absolute;"><a href="../gamerepo/home" style=" z-index: 1000; position: absolute;"><button class="ButtonStyle" type="submit" style=" z-index: 1000; position: absolute;">Exit</button></a></div>
    	<% // determine what player is where
    	Player playerLeft = null;
        Player playerRight = null; 
        Player playerTop = null; 
        Player playerBottom = null; 
        try {
        	if (game.getMaxPlayers() == 3) {
        		if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(0)) == db.getUserIDfromUsername(us)) { 
	   	    	    playerBottom = game.getIndexPlayer(0);
	   	    	    playerRight = game.getIndexPlayer(1);
	   	    	    playerLeft = game.getIndexPlayer(2);
	   	         } else if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(1)) == db.getUserIDfromUsername(us)) { 
	   	        	playerBottom = game.getIndexPlayer(1);
	   	        	playerRight = game.getIndexPlayer(2);
	   	        	playerLeft = game.getIndexPlayer(0);
	   	         } else { 
	   	        	playerBottom = game.getIndexPlayer(2);
	   	        	playerRight = game.getIndexPlayer(0);
	   	        	playerLeft = game.getIndexPlayer(1);
	   		     }
        	} else {
        		if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(0)) == db.getUserIDfromUsername(us)) { 
	   	    	    playerBottom = game.getIndexPlayer(0);
	   	    	    playerRight = game.getIndexPlayer(1);
	   	    	    playerTop = game.getIndexPlayer(2);
	   	    	    playerLeft = game.getIndexPlayer(3);
	   	         } else if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(1)) == db.getUserIDfromUsername(us)) { 
	   	        	playerBottom = game.getIndexPlayer(1);
	   	        	playerRight = game.getIndexPlayer(2);
	   	        	playerTop = game.getIndexPlayer(3);
	   	        	playerLeft = game.getIndexPlayer(0);
	   	         } else if (db.getUserBotIdFromPlayerId(game.getPlayerIds().get(2)) == db.getUserIDfromUsername(us)) { 
	   	        	playerBottom = game.getIndexPlayer(2);
	   	        	playerRight = game.getIndexPlayer(3);
	   	        	playerTop = game.getIndexPlayer(0);
	   	        	playerLeft = game.getIndexPlayer(1);
	   		     } else { 
	   		        playerBottom = game.getIndexPlayer(3);
	   			    playerRight = game.getIndexPlayer(0);
	   			    playerTop = game.getIndexPlayer(1);
	   			    playerLeft = game.getIndexPlayer(2);
	   		     }
        	}
        } catch (Exception e) {
        	response.sendRedirect("../home");
        }
        %>
    	
    	<div class="container" style="width: 100%; height: 100%;">
 
    	<div class="top" style="height: 25%; width: 100%; top: 0; position: static">
    		<% if (game.getMaxPlayers() == 4) { %>
	    		<% String topName = db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerTop)); %>
	    		<% if (db.getNameFromPlayerId(currentPlayerId).equals(topName)) { %>
	        		<div id="centers" style="background-color: #00ff00; color: #000000;"> <% out.print(topName); %> </div>
	        	<% } else { %>
	        		<div id="centers"> <% out.print(topName); %> </div>
	        	<% } %>	
	    		<div class="cards">
	            	<% for (int i = playerTop.getPile().getNumCards() ; i > 0 ; i --) { %>
		            	<img id="piliTop" src="_view/images/UnoCards/back.png" >
	            	<% }%>
	        	</div>
	        	<% if (playerTop.getPile().getNumCards() == 1) { %>
	    			<img src="_view/images/UNO.png" style="position: absolute; width: 8%; top:16%; right: 46%;">
	    		<% } %>
        	<% } %>
    	</div>
        <div class="left" style="width: 25%; height: 50%; left: 0; top: 25%; position: absolute">
        	<%
        	String leftName = db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerLeft));
        	if (playerLeft.getPile().getNumCards() == 1) { 
        		%><img src="_view/images/UNO.png" style="position: absolute; top: 0; right: 0; width: 32%;"><%
        	}
        	if (db.getNameFromPlayerId(currentPlayerId).equals(leftName)) {
        		%><div id="centersSides" style="margin-top: 1%; background-color: #00ff00; color: #000000;"> <% out.print(leftName); %> </div><%
        	} else {
       			%><div id="centersSides" style="margin-top: 1%;"> <% out.print(leftName); %> </div><%
       		}
        	%><div class="cards" style="margin-top: -5%;"><%
        	for (int i = playerLeft.getPile().getNumCards() ; i > 0 ; i --) {
            	%><img id="piliLeft" src="_view/images/UnoCards/back.png" style="margin-bottom: -25%;"><br><%
        	}
        	%>
	    	</div>
        </div>
        <% String bottomName = db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerBottom)); %>
        <div class="content">
        	<div class="split">
        		<div id="imgCenter" >
	        		<form method="post">
	        			<% if (currentPlayerId == db.getPlayerIdFromPlayer(playerBottom)) { %>
	        				<button type="submit" class="pili IFHY" name="Draw" value="Draw" style="width: 56%;" > <img src="_view/images/UnoCards/back.png"></button>
	        			<% } else { %>
	        				<button disabled type="submit" class="pili IFHY" name="Draw" value="Draw" style="width: 56%;" > <img src="_view/images/UnoCards/back.png"></button>
	        			<% } %>
	        		</form>
        		</div>
        	</div>
        	<% if (game.getTurnOrder().getAdder() > 0) { %>
        		<img src="_view/images/right_arrow.png" style="position: absolute; width: 20%; margin-left: -10%; bottom: -8%;">
        	<% } else { %>
        		<img src="_view/images/left_arrow.png" style="position: absolute; width: 20%; margin-left: -10%; bottom: -8%;">
        	<% } %>

			<%  // display little color circle
       			if (((UnoCard) game.getAltPile().getTopCard()).getColor() == Color.BLACK) {
       				switch (game.getWildColor()) {
        				case "R":
        					%><span class="dotRed" style="position: absolute; top: 30%; margin-left: -5%;"></span><%
        					break;
        				case "B":
        					%><span class="dotBlue" style="position: absolute; top: 30%; margin-left: -5%;"></span><%
        					break;
        				case "G":
        					%><span class="dotGreen" style="position: absolute; top: 30%; margin-left: -5%;"></span><%
        					break;
        				case "Y":
        					%><span class="dotYellow" style="position: absolute; top: 30%; margin-left: -5%;"></span><%
        					break;
        				default:
        					break;
       				}
       			}
        	%>
        	<div class="split">
        		<div id="imgCenter" >
        			<img class="deck" src="<%out.print(((UnoCard) game.getAltPile().getTopCard()).getImagePath());%>" style="width: 50%;">
				</div>
        	</div>
        	
        </div>
        
        <div class="right" style="width: 25%; height: 50%; right: 0; top: 25%; position: absolute;">
        	<%
       		String rightName = db.getNameFromPlayerId(db.getPlayerIdFromPlayer(playerRight));
       		int numRight = playerRight.getPile().getNumCards();
       		if (numRight == 1) {
       			%><img src="_view/images/UNO.png" style="position: absolute; top: 0; left: 0; width: 32%;"><%
       		}
       		if (db.getNameFromPlayerId(currentPlayerId).equals(rightName)) {
        		%><div id="centersSides" style="margin-top: 1%; background-color: #00ff00; color: #000000;"> <% out.print(rightName); %> </div><%
       		} else {
       			%><div id="centersSides" style="margin-top: 1%; "> <% out.print(rightName); %> </div><%
       		}
        	%><div class="" style="margin-top: -5%;"><%
        		for (int i = numRight ; i > 0 ; i --) {
	            	%><img id="piliRight" src="_view/images/UnoCards/back.png" style="margin-bottom: -25%; margin-left: 40%;"><%
	        	}%>
	        </div>
        </div>
        
        <div class="bottom">
        	<% if (playerBottom.getPile().getNumCards() == 1) { %>
        		<img src="_view/images/UNO.png" style="position: absolute; width: 8%; top:-20%; right: 46%;">
        	<% } %>
        	<% if (db.getNameFromPlayerId(currentPlayerId).equals(bottomName)) { %>
        		<div id="centers" style="background-color: #00ff00; color: #000000;"> <% out.print(bottomName); %> </div>
        	<% } else { %>
        		<div id="centers"> <% out.print(bottomName); %> </div>
        	<% } %>
        	<div class="cards" style="max-width: 100%; bottom: 0; height: 50%;"> 
	        	<form method="post" name="plc">
		        	<%
		        	if (currentPlayerId != db.getPlayerIdFromPlayer(playerBottom)) { 
			        	for (Object o : playerBottom.getPile().getPile()) { 
							UnoCard c = ((UnoCard) o); 
							if (c.getColor() != Color.BLACK) {
								%><button disabled id="pili" style="margin-left: -3%; width: 8%; height:auto; background: none; border: none;"> <img src="<%out.println(c.getImagePath());%>"></button><%
							} else {
								%><button disabled id="pili" style="margin-left: -3%; width: 8%; height:auto; background: none; border: none;"> <img src="<%out.println(c.getImagePath());%>"></button><%
							}
						}
					} else {
						for (Object o : playerBottom.getPile().getPile()) {
							UnoCard c = ((UnoCard) o);
							if (c.getColor() != Color.BLACK) {
								%><button type="submit"  id="pili" name="playCard" value="<% out.print(c); %>" style="margin-left: -3%; width: 8%; height:auto; background: none; border: none;"> <img src="<%out.println(c.getImagePath());%>"></button><%
							} else {
								%><button type="button" id="pili" onclick="colorFunction('<% out.print(c); %>')" name="playSpecialCard" value="<% out.print(c); %>" style="margin-left: -3%; width: 8%; height:auto; background: none; border: none;"> <img src="<%out.println(c.getImagePath());%>"></button><%
							}
						}
					}
					%>
					<script>

					function colorFunction(s) {
						// Create a form dynamically
					    var form = document.createElement("form");
					    form.setAttribute("method", "post");
					    form.setAttribute("action", "../gamerepo/uno");
					    
					    // create a hidden attribute for the card type
					    var h = document.createElement("input");
					    h.setAttribute("type", "hidden");
					    h.setAttribute("name", "card");
					    h.setAttribute("value", s);
					    
		                // create a submit button
		                var b = document.createElement("button");
		                b.setAttribute("type", "submit");
		                b.setAttribute("value", '<% out.print(Color.BLUE); %>');
		                b.setAttribute("name", "color");
		                b.setAttribute("style", "background-color: #0000ffd3; width: 50%; height: 50%; border: 5px solid #000000; position: absolute; top: 0; right: 0;");
		                
		                // create a submit button
		                var r = document.createElement("button");
		                r.setAttribute("type", "submit");
		                r.setAttribute("value", '<% out.print(Color.RED); %>');
		                r.setAttribute("name", "color");
		                r.setAttribute("style", "background-color: #ff0000d3; width: 50%; height: 50%; border: 5px solid #000000; position: absolute; top: 0; left: 0;");
		                
		                // create a submit button
		                var y = document.createElement("button");
		                y.setAttribute("type", "submit");
		                y.setAttribute("value", '<% out.print(Color.YELLOW); %>');
		                y.setAttribute("name", "color");
		                y.setAttribute("style", "background-color: #fff200ea; width: 50%; height: 50%; border: 5px solid #000000; position: absolute; bottom: 0; left: 0;");
		                
		                // create a submit button
		                var g = document.createElement("button");
		                g.setAttribute("type", "submit");
		                g.setAttribute("value", '<% out.print(Color.GREEN); %>');
		                g.setAttribute("name", "color");
		                g.setAttribute("style", "background-color: #00ff00e3; width: 50%; height: 50%; border: 5px solid #000000; position: absolute; bottom: 0; right: 0;");
		               
		                 
		                // Append the submit button to the form
		                form.appendChild(r);
		                form.appendChild(b);
		                form.appendChild(y);
		                form.appendChild(g);
		                form.appendChild(h);
		 				
		                // idek anymore
		                document.getElementsByTagName("body")[0].appendChild(form);
						
						// prep to auto submit form if user doesn't provide input
						setTimeout(submitForm, 10000, form);
					}
					
					// auto submit form with random color choice
					function submitForm(form) {
						choice = ['<% out.print(Color.RED); %>', '<% out.print(Color.BLUE); %>', '<% out.print(Color.GREEN); %>', '<% out.print(Color.YELLOW); %>'];
						c = choice[Math.floor(Math.random() * choice.length)];
						
						var h = document.createElement("input");
					    h.setAttribute("type", "hidden");
					    h.setAttribute("name", "color");
					    h.setAttribute("value", c);
					    form.appendChild(h);
					    
						form.submit();
					}
				</script>
				</form>
			</div>
        </div>
    </div>        
    
    <%if (game.getTurnOrder().getTurnList().size() < game.getMaxPlayers()) { %>
	   	<a href="../gamerepo/home">
	   	<% if (playerBottom.getPile().getNumCards() == 0) { %>
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