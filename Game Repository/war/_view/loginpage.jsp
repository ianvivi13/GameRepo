<!DOCTYPE html>

<html>
	<head>

		<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
		<link rel="stylesheet" type="text/css" href="_view/css/login.css"/>
		<link rel="stylesheet" type="text/css" href="_view/css/general.css"/>
		<title>Login</title>
	</head>
	
	<body class=StaticBackground>
		<div class="login">
			<div class="title">Login</div>
			<div class="FormStyle">
				<form class="form" action="${pageContext.servletContext.contextPath}/login" method="post">
					<div class="InputStyle">
						<input id="uword" type="text" name="Username" placeholder="Username" required>
					</div>
					<div class="InputStyle">
						<input id="pword" type="password" name="Password" placeholder="Password" required>
					</div>
		
						<button class="ButtonStyle" type="submit">Sign In</button>
					
					<a href="http://localhost:8080/gamerepo/home">
						<button class="ButtonStyle" type="submit">Play as Guest</button>
					</a>
					<div class="signup">
						<a href="http://localhost:8080/gamerepo/new">Create New Account</a>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>