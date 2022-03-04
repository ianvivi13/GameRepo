<!DOCTYPE html>

<html>
	<head>
		<title>Stats</title>
        <link rel="stylesheet" href="_view/homepage.css" />
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