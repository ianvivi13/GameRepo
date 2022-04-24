<!DOCTYPE html>

<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/join.css"/>
        <title>Join Lobby</title>
    </head>
    
    <body class=StaticBackground>
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
            document.body.style.backgroundImage = "url('_view/Back.png')";
        }
        </script>
    	<div class="HeaderStyle">
    		Join Lobby
    	</div>
    	<div class="BackButton">
    		<a href="http://localhost:8080/gamerepo/home">
            	<button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
    	</div>
        <div class="AccountCreation">
            <div class="title">Enter Game ID</div>
            <form class="FormStyle">
                <div class="InputStyle">
                    <input type="text" placeholder="ID: XXXXX-XXXXX" maxlength=11 pattern="[A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]-[A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9][A-Z0-9]" title="GAME ID: 5 alphanumerics followed by a dash and 5 more" required>
                </div>
                <button class="ButtonStyle" type="submit">Join Game</button>
            </form>
        </div>
    </body>
</html>