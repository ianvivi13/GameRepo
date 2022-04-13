<!DOCTYPE html>

<html>
	<head>
		<title>Home Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/homepage.css"/>
	</head>
	
	<body class = "StaticBackground">
		<div class="HeaderStyle">
    		Welcome:&nbsp;Admin
    	</div>

        <div class = "BackButton">
			<a href="http://localhost:8080/gamerepo/login">
            	<button class="ButtonStyle" type="submit">Log Out</button>
            </a>
        </div>

		<div class = "buttons">
			<a href="http://localhost:8080/gamerepo/instructions">
            	<button class="ButtonStyle" type="submit">Instructions</button>
            </a>
        </div>

		<div class = "buttons">
			<a href="http://localhost:8080/gamerepo/singleplayer">
            	<button class="ButtonStyle" type="submit">Singleplayer</button>
            </a>
		</div>

		<div class = "buttons">
			<a href="http://localhost:8080/gamerepo/multiplayer">
            	<button class="ButtonStyle" type="submit">Multiplayer</button>
            </a>
		</div>

		<div class = "buttons">
			<a href="http://localhost:8080/gamerepo/stats">
            	<button class="ButtonStyle" type="submit">Stats</button>
            </a>
		</div>
		
		<div id = "bootybutt">
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
				<img src="_view/images/ExplodingKittens/back.jpg">
			</label>

			<label class = "buttspace">
				<input type="radio" name="butt" value="expoldingkittens">
				<img src="_view/images/ExplodingKittens/back.jpg">
			</label>
		</div>
	</body>
</html>