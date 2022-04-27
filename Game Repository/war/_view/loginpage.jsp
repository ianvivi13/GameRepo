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
		<script>
			localStorage.clear();
		</script>
		<div class="login">
			<div class="title">Login</div>
			<div class="FormStyle">
				<form class="form" action="${pageContext.servletContext.contextPath}/login" method="post">
					<div class="InputStyle">
						<input id="uword" type="text" name="Username" placeholder="Username" value="${username}" required>
					</div>
					<div class="InputStyle">
						<input id="pword" type="password" name="Password" placeholder="Password" value="${password}" required>
					</div>
		
						<button class="ButtonStyle" type="submit">Sign In</button>
					
					<a href="http://localhost:8080/gamerepo/new">
						<button class="ButtonStyle" form="false">Create New Account</button>
					</a>
					
				</form>
			</div>
		</div>
	</body>
</html>