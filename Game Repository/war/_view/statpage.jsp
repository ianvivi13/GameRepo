<!DOCTYPE html>

<html>
	<head>
		<title>Stats</title>
        <link rel="stylesheet" href="_view/statpage.css" />
	</head>
	
	<body>
        <div id = "top">
            <p1>Stats:&nbsp;Username</p1>
            <div class = "mainMenu">
                <form method="get" action="http://localhost:8080/gamprepo/home">
                    <input type="Submit" name="submit" value="Main Menu">
                </form>
            </div>
        </div>

        <div id = "globby">
            <div id="rank">
                <h3 class="tit">Global Rank</h3>
                <p> 8 </p>
            </div>
    
            <div id="globstat">
                <h3 class="tit">Global Stats</h3>
                <p>Games Played: </p> <br>
                <p>Games Won: </p> <br>
                <p>Games Lost: </p> <br>
                <p>Win/Loss Ratio: </p> <br>
            </div>
        </div>
	</body>
</html>