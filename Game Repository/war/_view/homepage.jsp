<!DOCTYPE html>

<html>
	<head>
		<title>Home</title>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/homepage.css"/>
	</head>
	
	<body>
        <div id = "top">
            <p1>Welcome:&nbsp;Username</p1>
            <div class = "homePage">
                <form method="get" action="http://localhost:8080/gamerepo/login">
                    <input type="Submit" name="submit" value="Logout">
                </form>
            </div>
        </div>

		<div class = "buttons">
			<form method="get" action="http://localhost:8080/gamerepo/instructions">
				<input type="Submit" name="submit" value="Instructions">
			</form>
		</div>

		<div class = "buttons">
			<form method="get" action="http://localhost:8080/gamerepo/singleplayer">
				<input type="Submit" name="submit" value="SinglePlayer">
			</form>
		</div>

		<div class = "buttons">
			<form method="get" action="http://localhost:8080/gamerepo/multiplayer">
				<input type="Submit" name="submit" value="MultiPlayer">
			</form>
		</div>

		<div class = "buttons">
			<form method="get" action="http://localhost:8080/gamerepo/stats">
				<input type="Submit" name="submit" value="Stats">
			</form>
		</div>

	</body>
</html>