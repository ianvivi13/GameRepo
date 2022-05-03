<!DOCTYPE html>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/join.css"/>
        <title>Join Lobby</title>

        <%@page import="Models.Game" %>
        <%@page import= "Database.elves.DatabaseProvider" %>
        <%@page import= " Database.elves.DerbyDatabase" %>
        <%@page import= "Database.elves.IDatabase" %>
        <%@page import= "Database.elves.InitDatabase" %>
        <%InitDatabase.init(); %>
        <%IDatabase db = DatabaseProvider.getInstance(); %>
    </head>
    
    <body class=StaticBackground>
        <script>
        let value = localStorage.getItem("buttonValue");
        let x = null;
        console.log(value);

        switch (value) {
        case 'blackjack':
        	
            document.body.style.color = "black";
            document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

            localStorage.setItem("buttonValue", 'blackjack');
            console.log(value);
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
            console.log(x);
            break;

        default:
            document.body.style.color = "white";
            document.body.style.backgroundImage = "url('_view/css/Back.png')";
        }

        </script>
    	<div class="HeaderStyle">
    		${user}
    	</div>
    	<div class="BackButton">
    		<a href="../gamerepo/home">
            	<button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
    	</div>
        <div class="AccountCreation">
            <div class="title">Enter Game ID</div>
            <form class="FormStyle">
                <div class="InputStyle">
                    <input type="text" id="autoFill" name="GameId" placeholder="ID: XXXXX-XXXXX" maxlength=11 pattern="[A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]-[A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]" title="GAME ID: 5 alphanumerics followed by a dash and 5 more" required>
                </div>
                <button class="ButtonStyle" type="submit">Join Game</button>
            </form>
            <p>_______________________________________________________</p>
            <div>
            	<% for (Game g : db.getGameListFromGameKey()) { %>
            		<% int max = g.getMaxPlayers(); %>
            		<% int tot = g.getNumOfPlayers(); %>
            		<% String code = g.getGameCode(); %>
            		<% String gameKey = g.getGameKey(); %>
            		<% try { %>
            			<% String hostName = db.getNameFromPlayerId(g.getPlayerIds().get(0)); %>
            			<button class="ButtonStyle" style="width: 90%;" onclick="document.getElementById('autoFill').value='<% out.print(code); %>';"> <% out.print(gameKey); %>&ensp;&ensp;&ensp;<% out.print(hostName); %>&ensp;&ensp;&ensp;<% out.print(tot); %> / <% out.print(max); %> </button><p></p>
            		<% } catch (Exception e) {} %>
            	<% } %>
        	</div>
        	
        </div>
    </body>
</html>