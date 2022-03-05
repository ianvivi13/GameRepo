<!DOCTYPE html>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/join.css"/>
        <title>Join Lobby</title>
    </head>
    
    <body class=StaticBackground>
    	<div class="HeaderStyle">
    		Join Lobby
    	</div>
    	<div class="BackButton">
    		<a href="http://localhost:8080/gamerepo/home">
            	<button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
    	</div>
        <div class="AccountCreation">
            <div class="title">Enter Game ID</div>
            <form class="FormStyle">
                <div class="InputStyle">
                    <input type="text" placeholder="ID: XXXXX-XXXXX" maxlength=11 pattern="[A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]-[A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]" title="GAME ID: 5 alphanumerics followed by a dash and 5 more" required>
                </div>
                <button class="ButtonStyle" type="submit">Join Game</button>
            </form>
        </div>
    </body>
</html>