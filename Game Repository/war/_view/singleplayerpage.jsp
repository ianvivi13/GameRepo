<!DOCTYPE html>
<html>
    <head>
    	<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/host.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/multiplayer.css"/>
        <title>
            Singleplayer
        </title>
    </head>

    <body class="StaticBackground">
        <script>
            let value = localStorage.getItem("buttonValue");
            console.log(value);

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

        </script>
        
        <div class="HeaderStyle">Singleplayer: ${user}</div>
        
        <div class="BackButton">
            <a href="http://localhost:8080/gamerepo/home"><button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
    </div>

    <h2 id="topic"><u>Card Game Desired</u></h2>
    
    <div class="topics"><span class="underline">Difficulty Level</span>
    <div class="difficulty-slider">
        <input id="difficultyEasy" type="radio" name="difficulty">
        <label for="difficultyEasy">Easy</label>
        <input id="difficultyMedium" type="radio" name="difficulty" checked>
        <label for="difficultyMedium">Medium</label>
        <input id="difficultyHard" type="radio" name="difficulty">
        <label for="difficultyHard">Hard</label>
      </div>
      </div>

      <div class="topics"><span class="underline">Bot Count</span>
      <div class="bot-slider">
        <input id="bot1" type="radio" name="bot">
        <label for="bot1">1</label>
        <input id="bot2" type="radio" name="bot">
        <label for="bot2">2</label>
        <input id="bot3" type="radio" name="bot" checked>
        <label for="bot3">3</label>
        <input id="bot4" type="radio" name="bot">
        <label for="bot4">4</label>
        <input id="bot5" type="radio" name="bot">
        <label for="bot5">5</label>
      </div>
      </div>

      <label class="ckb" id="slider"><span class="underline"> Doubling</span>: &nbsp;
            <input type="checkbox" name="ckb1" value="a" unchecked>
            <i></i>
          </label>
          <br><br>

    <a href="http://localhost:8080/gamerepo/singleplayer"><button class="branch" type="submit">Play</button><br>
    </a>
    </body>
</html>





