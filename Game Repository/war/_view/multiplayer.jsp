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
                    
                document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
                localStorage.setItem("buttonValue", 'uno');
                console.log("buttonValue");
                break;    

            case 'unoflip':
                    
                document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
                localStorage.setItem("buttonValue", 'unoflip');
                console.log("buttonValue");
                break;

            case 'expoldingkittens':

                document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
                localStorage.setItem("buttonValue", 'expoldingkittens');
                console.log("buttonValue");
                break;

            default:
        
            document.body.style.backgroundImage = "url('_view/Back.png')";
        }
		  </script>
        
        <div class="HeaderStyle">Multiplayer:&nbsp;Admin</div>
        
        <div class="BackButton">
            <a href="http://localhost:8080/gamerepo/home"><button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
    </div>

    <h2 id="topic"><u>Card Game Desired</u></h2>
    
    <a href="http://localhost:8080/gamerepo/host"><button class="branch" type="submit">Host</button><br>
    </a>
    
    <a href="http://localhost:8080/gamerepo/join"><button class="branch" type="submit">Join</button>
    </a>

    </body>
</html>