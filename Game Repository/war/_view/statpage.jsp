<!DOCTYPE html>

<html>
	<head>
		<title>Stats</title>
		<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/statpage.css"/>
	</head>
	
	<body class="StaticBackground">
        <div class = "HeaderStyle">
            Stats:&nbsp;Admin
        </div>
    
        <div class = "BackButton">
            <a href="http://localhost:8080/gamerepo/home">
                <button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
        </div>    

        <div id = "glob">
            <div id = "globalrank">
                <span id = "tit"><h3>Global Rank</h3></span>
                <span id = "text"><p>8</p></span>
            </div>
    
            <div id = "stats">
                <span id = "tit"><h3>Global Stats</h3></span>
                <p>Games Played:&nbsp; 100</p>
                <p>Games Won:&nbsp; 40</p>
                <p>Games Loss:&nbsp; 60</p>
                <p>Win/Loss Ratio:&nbsp; 0.67</p>                   
            </div>
        </div>

        <div id = "bootybutt">
            <label class = "buttspace">
                <input type="radio" name="butt" id="rad1"  data-toggle-value = "blackjack">
                <img src="_view/css/Standard Card/back-sm.png">
            </label>

            <label class = "buttspace">
                <input type="radio" name="butt" id="rad2" data-toggle-value = "uno">
                <img src="_view/css/Standard Card/back-sm.png">
            </label>

            <label class = "buttspace">
                <input type="radio" name="butt" id="rad3" data-toggle-value = "unoflip">
                <img src="_view/css/Standard Card/back-sm.png">
            </label>

            <label class = "buttspace">
                <input type="radio" name="butt" id="rad4" data-toggle-value = "exploding kittens">
                <img src="_view/css/Standard Card/back-sm.png">
            </label>
        </div>

	</body>
</html>