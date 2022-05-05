<!DOCTYPE html>

<html>
    <head>
    	<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/multiplayer.css"/>
        <title>
            Multiplayer
        </title>
    </head>

    <body class="StaticBackground">
    	<audio id="audioplayer" loop="false" autoplay="true" preload="auto" src="_view/background.mp3"></audio>
    	<script src="_view/audio.js"></script>
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
		  
        <div class="HeaderStyle">${user}</div>
        
        <div class="BackButton">
            <a href="../gamerepo/home"><button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
    </div>
	
	<p></p>
    
    <a href="../gamerepo/host"><button class="branch" type="submit">Host</button><br>
    </a>
    <p></p>
    <a href="../gamerepo/join"><button class="branch" type="submit">Join</button>
    </a>

    </body>
</html>