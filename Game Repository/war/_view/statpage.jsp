<!DOCTYPE html>

<html>
	<head>
		<title>Stats</title>
		<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/statpage.css"/>

        <%@page import= "Database.elves.DatabaseProvider" %>
        <%@page import= " Database.elves.DerbyDatabase" %>
        <%@page import= "Database.elves.IDatabase" %>
        <%@page import= "Database.elves.InitDatabase" %>

        <%@page import="Models.StatisticsBlackjack" %>
        <%@page import="Models.StatisticsParent" %>
        <%@page import="Models.StatisticsExplodingKittens" %>
        <%@page import="Models.StatisticsUno" %>
        <%@page import="Models.StatisticsUnoFlip" %>
	</head>
	
	<body class="StaticBackground">
		<audio id="audioplayer" loop="false" autoplay="true" preload="auto" src="_view/background.mp3"></audio>
    	<script src="_view/audio.js"></script>
        <script>
        let value = localStorage.getItem("buttonValue");

    <%  
        InitDatabase.init();
        IDatabase db = DatabaseProvider.getInstance();
        String us = (String) session.getAttribute("user");

        StatisticsParent glob = db.getGlobalStats(us);
        StatisticsBlackjack blj = db.getBlackjackStats(us);
        StatisticsUno uno = db.getUnoStats(us);
        StatisticsExplodingKittens exp = db.getExplodingKittenStats(us);
        StatisticsUnoFlip flip = db.getUnoFlipStats(us);
    %>

        function radioClicked(){
            let shapeChoice = document.querySelector('input[name="butt"]:checked').value;
            switch (shapeChoice) {
            case 'blackjack':

                document.body.style.color = "white";
                localStorage.setItem("buttonValue", 'blackjack');
                console.log("buttonValue");
            
                document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Blackjack Stats</h3></span>
                <p>Times Held:&nbsp; <% out.print(blj.GetHolds()); %> </p> 
                <p>Hits Taken:&nbsp; <% out.print(blj.GetHits()); %> </p>
                <p>Times Froze:&nbsp; <% out.print(blj.GetFroze()); %> </p>`
                break;

            case 'uno':

                document.body.style.color = "white";
                localStorage.setItem("buttonValue", 'uno');
                console.log("buttonValue");
                
                document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";    

                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Uno Stats</h3></span>
                <p>WildCardsPlayed:&nbsp; <% out.print(uno.GetWildCards()); %></p>
                <p>DrawFoursPlayed:&nbsp; <% out.print(uno.GetPlusFours()); %></p>
                <p>ReversesPlayed:&nbsp; <% out.print(uno.GetReverses()); %></p>`
                break;

            case 'unoflip':

                document.body.style.color = "white";
                localStorage.setItem("buttonValue", 'unoflip');
                console.log("buttonValue");
                
                document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
                
                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Uno-Flip Stats</h3></span>
                <p>FlipsPlayed:&nbsp; <% out.print(flip.GetFlips()); %></p>
                <p>DrawFivesPlayed:&nbsp; <% out.print(flip.GetPlusFives()); %></p>
                <p>SkipAllsPlayed:&nbsp; <% out.print(flip.GetSkipAlls()); %></p>`
                break;

            case 'expoldingkittens':

                document.body.style.color = "white";
                localStorage.setItem("buttonValue", 'expoldingkittens');
                console.log("buttonValue");

                document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
                
                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Exploding Kittens Stats</h3></span>
                <p>DefusesPlayed:&nbsp; <% out.print(exp.GetDefuses()); %></p>
                <p>FavorsAsked:&nbsp; <% out.print(exp.GetFavors()); %></p>
                <p>FuturesSeen:&nbsp; <% out.print(exp.GetFutures()); %></p>`
                break;

            default:

                document.body.style.backgroundImage = "url('_view/css/Back.png')";
                document.body.style.color = "white";

                doucment.getElementById("stats").innerHTML = 
                '<iframe src="https://giphy.com/embed/GeimqsH0TLDt4tScGw" style="text-align:center" width="480" height="320" frameBorder="0" class="giphy-embed" allowFullScreen></iframe><p><a href="https://giphy.com/gifs/vibes-vibing-vibin-GeimqsH0TLDt4tScGw"></a></p> '           
            }
        };

                radioClicked();
        </script>


        <div class = "HeaderStyle">
            ${user}
        </div>
    
        <div class = "BackButton">
            <a href="../gamerepo/home">
                <button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
        </div>    

        <div id = "glob">
            <div id = "stats">
                <iframe src="https://giphy.com/embed/GeimqsH0TLDt4tScGw" style="text-align:center" width="480" height="320" frameBorder="0" class="giphy-embed" allowFullScreen></iframe><p><a href="https://giphy.com/gifs/vibes-vibing-vibin-GeimqsH0TLDt4tScGw"></a></p>            
            </div>
        </div>

        <div id = "bootybutt" onload="radioClicked()" onclick="radioClicked()">
            <label class = "buttspace">
                <input type="radio" name="butt" value="blackjack">
                <img src="_view/images/StandardCards/back-sm.png">
            </label>

            <label class = "buttspace">
                <input type="radio" name="butt" value="uno">
                <img src="_view/images/UnoCards/back.png">
            </label>

            <label class = "buttspace">
                <input type="radio" name="butt" value="unoflip">
                <img src="_view/images/UnoFlipCards/FlipDark/v_f.jpg">
            </label>

            <label class = "buttspace">
                <input type="radio" name="butt" value="expoldingkittens">
                <img src="_view/images/ExplodingKittens/back.jpg">
            </label>
        </div>

	</body>
</html>