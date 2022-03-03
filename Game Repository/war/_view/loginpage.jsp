<!DOCTYPE html>

<html>
	<head>
		<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="_view/css/login.css"/>
		<title>Login</title>
	</head>
	
	<body>
		<div class="login">
			<div class="title">Login</div>
			<div class="form">
				<div class="username">
					<input type="text" placeholder="Username">
				</div>
				<div class="password">
					<input type="password" placeholder="Password">
				</div>
					<a href="http://localhost:8080/gamerepo/home">
						<button class="signin" type="submit">Sign In</button>
					</a>
					<a href="http://localhost:8080/gamerepo/home">
						<button class="signup" type="submit">Play as Guest</button>
					</a>
					<div class="guest">
						<a href="https://google.com">Create New Account</a>
					</div>
			</div>
		</div>
	</body>
</html>