<!DOCTYPE html>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/newaccount.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/general.css"/>
        <title>Create Account</title>
        <script>
			function validate(){
			  let i1 = document.querySelector("#pass").value;
			  let i2 = document.querySelector("#passconfirm").value;
			  if(i1 === i2){
				// Login user here by submitting values to model and moving to home page
			    return true
			  }else{
			    alert("Passwords do not match");
			    event.preventDefault()
			    return false
			  }
			}
		</script>
    </head>
    <body class=StaticBackground>
    	<div class="BackButton">
    		<a href="../gamerepo/login">
            	<button class="ButtonStyle" type="submit">Back to Login</button>
            </a>
    	</div>
        <div class="AccountCreation">
            <div class="title">Create Account</div>
            <form class="FormStyle" action="${pageContext.servletContext.contextPath}/new" method="post">
                <div class="InputStyle">
                  <input type="text" name="Username" placeholder="Username" maxlength=20 pattern="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$" title="USERNAME: Alpanumeric characters & symbols: -._ (not first or last character) & 5-20 characters" required>
                </div>
                <div class="InputStyle">
                  <input type="password" name="Password" id="pass" placeholder="Password" maxlength=32 pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,32}$" title="PASSWORD: Must contain at least one number & one uppercase & lowercase letter & one special character & 8-32 characters" required>
                </div>
                <div class="InputStyle">
                  <input type="password" name="PassConfirm" id="passconfirm" placeholder="Re-enter Password" maxlength=32 required>
                </div>
                <button class="ButtonStyle" type="submit" onclick="validate()" >Create Account</button>
            </form>
        </div>
    </body>
</html>