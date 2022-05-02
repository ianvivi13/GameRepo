<!DOCTYPE html>

<html>
	<head>
		<title>Home Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/homepage.css"/>
	</head>
	
	<body class = "StaticBackground">

		<script>
		let value = localStorage.getItem("buttonValue");
		console.log(value);

		//if (localStorage.getItem("buttonValue") == 'null' || localStorage.getItem("buttonValue") == null) {
		//	document.body.style.backgroundImage = "url('_view/Back.png')";
		//}

			switch (value) {
			case 'blackjack':
				
				document.body.style.color = "black";
				document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

				localStorage.setItem("buttonValue", 'blackjack');
				console.log("buttonValue");
				break;

			case 'uno':

				document.body.style.color = "black";
				document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
				localStorage.setItem("buttonValue", 'uno');
				console.log("buttonValue");
				break;    

			case 'unoflip':

				document.body.style.color = "white";	
				document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
				localStorage.setItem("buttonValue", 'unoflip');
				console.log("buttonValue");
				break;

			case 'expoldingkittens':

				document.body.style.color = "white";
				document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
				localStorage.setItem("buttonValue", 'expoldingkittens');
				console.log("buttonValue");
				break;

			default:
			
			document.body.style.color = "white";
			document.body.style.backgroundImage = "url('_view/css/Back.png')";
		}
	

			function radioClicked(){
				var shapeChoice = document.querySelector('input[name="butt"]:checked').value;
	
				switch (shapeChoice) {
				case 'blackjack':
					
					document.body.style.color = "black";
					document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

					localStorage.setItem("buttonValue", 'blackjack');
					console.log("buttonValue");
					break;
	
				case 'uno':
						
					document.body.style.color = "black";
					document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
					localStorage.setItem("buttonValue", 'uno');
					console.log("buttonValue");
					break;    
	
				case 'unoflip':
					  
					document.body.style.color = "white";
					document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
					localStorage.setItem("buttonValue", 'unoflip');
					console.log("buttonValue");
					break;
	
				case 'expoldingkittens':
	
					document.body.style.color = "white";
					document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
					localStorage.setItem("buttonValue", 'expoldingkittens');
					console.log("buttonValue");
					break;

					default:
					document.body.style.color = "white";
                    document.body.style.backgroundImage = "url('_view/css/Back.png')";
				}
			};
					radioClicked();
					
					function gameChoice() {
			</script>
<<<<<<< HEAD
		
			<div class="HeaderStyle">
	    		Welcome: ${user}
	    	</div>
	
	        <form class = "BackButton">
				<a href="../gamerepo/login">
	            	<button class="ButtonStyle" name="logout" type="submit">Log Out</button>
	            </a>
	        </form>
	
	        <form>
				<div class = "buttons">
					<a href="../gamerepo/instructions">
		            	<button class="ButtonStyle" type="submit">Instructions</button>
		            </a>
		        </div>
		
				<div class = "buttons">
					<a href="../gamerepo/singleplayer">
		            	<button class="ButtonStyle" name="return gameChoice()" type="submit">Singleplayer</button>
		            </a>
				</div>
		
				<div class = "buttons">
					<a href="../gamerepo/multiplayer">
		            	<button class="ButtonStyle" name="return gameChoice()" type="submit">Multiplayer</button>
		            </a>
				</div>
		
				<div class = "buttons">
					<a href="http://localhost:8080/gamerepo/stats">
		            	<button class="ButtonStyle" type="submit">Stats</button>
		            </a>
				</div>
			</form>
=======

		<div class="HeaderStyle">
    		Welcome: ${user}
    	</div>

        <form class = "BackButton">
			<a href="../gamerepo/login">
            	<button class="ButtonStyle" name="logout" type="submit">Log Out</button>
            </a>
        </form>

		<div class = "buttons">
			<a href="../gamerepo/instructions">
            	<button class="ButtonStyle" type="submit">Instructions</button>
            </a>
        </div>

		<div class = "buttons">
			<a href="../gamerepo/singleplayer">
            	<button class="ButtonStyle" type="submit">Singleplayer</button>
            </a>
		</div>

		<div class = "buttons">
			<a href="../gamerepo/multiplayer">
            	<button class="ButtonStyle" type="submit">Multiplayer</button>
            </a>
		</div>

		<div class = "buttons">
			<a href="../gamerepo/stats">
            	<button class="ButtonStyle" type="submit">Stats</button>
            </a>
		</div>
>>>>>>> refs/remotes/origin/BlackjackMatchmakingExtended
		
		<div id = "bootybutt" onload="radioClicked()" onclick="radioClicked()">
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
				<img src="_view/images/UnoFlipCards/FlipDark/v_f.jpg">
			</label>

			<label class = "buttspace">
				<input type="radio" name="butt" value="expoldingkittens">
				<img src="_view/images/ExplodingKittens/back.jpg">
			</label>
		</div>
	</body>
</html>