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
    <body class="Uno" style="overflow: hidden; height: 98%; box-shadow: inset 0 0 0 50vw rgba(0,0,0,0.3);">
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
        <div id="left" style="margin-bottom: -3%"><a href="../gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button>
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
	            		<img id="piliLeft" src="_view/images/UnoCards/back.png" style="margin-bottom: -25%;"></br>
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
  
        		<button type="submit" class="pili IFHY" name="Draw" value="Draw" style="width: 56%;" > <img src="_view/images/UnoCards/back.png"></button>
        		
        		</div>
        	</div>
        	</form>
        	
        	<div class="split">
        		<div id="imgCenter" >
        			<%  
        			if (((UnoCard) game.getAltPile().getTopCard()).getColor() == Color.BLACK) {
        				switch (game.getWildColor()) {
	        				case "R":
	        					%><span class="dotRed"></span><%
	        					break;
	        				case "B":
	        					%><span class="dotBlue"></span><%
	        					break;
	        				case "G":
	        					%><span class="dotGreen"></span><%
	        					break;
	        				case "Y":
	        					%><span class="dotYellow"></span><%
	        					break;
	        				default:
	        					break;
        				}
        				
        			}
        			%>
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
	            		<img id="piliRight" src="_view/images/UnoCards/back.png" style="margin-bottom: -25%; margin-left: 40%;"></br>
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
        	
        	<div class="cards" style="max-width: 100%; border: 2px solid red;">
        	<%int cNum = 0; %>
        	
        	<form method="post" name="plc">
        	<% for (Object o : playerBottom.getPile().getPile()) { %>
				<% UnoCard c = ((UnoCard) o); %>
				<% String pathBottom = c.getImagePath(); %>
				
				<% if (c.getColor() != Color.BLACK) {%>
					<button type="submit" id="pili" name="playCard" value="<% out.print(c); %>" style="margin-left: -3%; width: 8%; height:auto; background: none; border: none;"> <img src="<%out.println(pathBottom);%>"></button>
				<% } else { %>
					<button type="button" id="pili" onclick="colorFunction('<% out.print(c); %>')" name="playSpecialCard" value="<% out.print(c); %>" style="margin-left: -3%; width: 8%; height:auto; background: none; border: none;"> <img src="<%out.println(pathBottom);%>"></button>
				<% } %>
				<% cNum ++; %>
			<% } %>
				<script>
					function colorFunction(s) {
						//alert("boob");

						// Create a form dynamically
					    var form = document.createElement("form");
					    form.setAttribute("method", "post");
					    form.setAttribute("action", "../gamerepo/uno");
					    
					    var h = document.createElement("input");
					    h.setAttribute("type", "hidden");
					    h.setAttribute("name", "card");
					    h.setAttribute("value", s);
					    
		                // create a submit button
		                var b = document.createElement("button");
		                b.setAttribute("type", "submit");
		                b.setAttribute("value", '<% out.print(Color.BLUE); %>');
		                b.setAttribute("name", "color");
		                b.setAttribute("style", "background-color: #0000ffd3; width: 50%; height: 50%; border-width: 0; position: absolute; top: 0; right: 0;");
		                
		                // create a submit button
		                var r = document.createElement("button");
		                r.setAttribute("type", "submit");
		                r.setAttribute("value", '<% out.print(Color.RED); %>');
		                r.setAttribute("name", "color");
		                r.setAttribute("style", "background-color: #ff0000d3; width: 50%; height: 50%; border-width: 0; position: absolute; top: 0; left: 0;");
		                
		                // create a submit button
		                var y = document.createElement("button");
		                y.setAttribute("type", "submit");
		                y.setAttribute("value", '<% out.print(Color.YELLOW); %>');
		                y.setAttribute("name", "color");
		                y.setAttribute("style", "background-color: #fff200ea; width: 50%; height: 50%; border-width: 0; position: absolute; bottom: 0; left: 0;");
		                
		                // create a submit button
		                var g = document.createElement("button");
		                g.setAttribute("type", "submit");
		                g.setAttribute("value", '<% out.print(Color.GREEN); %>');
		                g.setAttribute("name", "color");
		                g.setAttribute("style", "background-color: #00ff00e3; width: 50%; height: 50%; border-width: 0; position: absolute; bottom: 0; right: 0;");
		               
		                 
		                // Append the submit button to the form
		                form.appendChild(r);
		                form.appendChild(b);
		                form.appendChild(y);
		                form.appendChild(g);
		                form.appendChild(h);
		 
		                document.getElementsByTagName("body")[0].appendChild(form);
						
						
						setTimeout(submitForm, 5000, form);
					}
					
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

 
    </body>
</html>