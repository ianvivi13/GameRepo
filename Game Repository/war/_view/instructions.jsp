<!DOCTYPE html>
<html>
    <head>
    	<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/multiplayer.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/homepage.css"/>
        <title>
            Multiplayer
        </title>
    </head>

    <body class="StaticBackground">
        <script>
            
            let value = localStorage.getItem("buttonValue");

            switch (value) {
            case 'blackjack':
                
                document.body.style.color = "white";
                document.getElementById("topic").innerText = "BlackJack";
                document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

                localStorage.setItem("buttonValue", 'blackjack');
                console.log(value);

                break;

            case 'uno':
                    
                document.body.style.color = "white";
                document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
                localStorage.setItem("buttonValue", 'uno');
                console.log(value);

                break;    

            case 'unoflip':
                    
                document.body.style.color = "white";
                document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
                localStorage.setItem("buttonValue", 'unoflip');
                console.log(value);
                    
                break;

            case 'expoldingkittens':

            document.body.style.color = "white";
                document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
                localStorage.setItem("buttonValue", 'expoldingkittens');
                console.log(value);

                break;

            default:
        
            document.body.style.color = "white";
            document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
        }
    
        function radioClicked(){
            let shapeChoice = document.querySelector('input[name="butt"]:checked').value;

            switch (shapeChoice) {
            case 'blackjack':

                document.body.style.color = "white";
                document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
                localStorage.setItem("buttonValue", 'blackjack');

                document.getElementById("topic").innerHTML = "BlackJack"
                document.getElementById("unordered").innerHTML = 
                `<li>The goal of blackjack is for the player to tally points up to 21</li>
                <li>The player is given a choice to "Hit," "Freeze," or "Hold."</li>
                <li>Choosing "Hold" makes the player skip their turn and wait until the turn-order comes back around</li>
                <li>Choosing "Hit" makes the player take a card from the deck</li>
                <li>You will be given a card from a 52-standard card deck</li>
                <li>Each card equals thier value but Kings, Queens, and Jacks are 10 points</li>
                <li>Aces allow the user to choose between the card's values of 11 or 1</li>
                <li>If the player goes over this limit of 21, they are removed from the game</li>
                <li>No other player will know the amount of points you have or what card you have</li>`
                break;

            case 'uno':

                document.body.style.color = "white";
                document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
                localStorage.setItem("buttonValue", 'uno');
                
                document.getElementById("topic").innerHTML = "Uno"
                document.getElementById("unordered").innerHTML = `<li>The goal of Uno is for the player to lose all of their cards</li>
                <li>The player will start their hand with 7 cards randomly selected from the deck</li>
                <li>To place a card, it must either match the color or number/symbol of the card in the waste pile</li>
                <li>Wild cards can be placed at any point of a player's turn</li>
                <li>Reverse cards reverse the turn order</li>
                <li>Skip cards skip the next player</li>
                <li>+2 cards forces the next player to draw two extra cards</li>
                <li>+4 Wild cards allow the player to change the color and forces the next player to draw four extra cards</li>
                <li>If the player has one card remaining, they must say "Uno" or they must draw 2 cards if an opponent says it first</li>
                <li>If a player loses all cards, the game ends</li>`
                break;

            case 'unoflip':

                document.body.style.color = "white";
                document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
                localStorage.setItem("buttonValue", 'unoflip');

                document.getElementById("topic").innerHTML = "Uno-Flip"
                document.getElementById("unordered").style.borderColor = "yellow";
                document.getElementById("unordered").innerHTML = `<li>The goal of Uno-Flip is for the player to lose all of their cards</li>
                <li>The player will start their hand with 7 cards randomly selected from the deck</li>
                <li>To place a card, it must either match the color or number/symbol of the card in the waste pile</li>
                <li>Wild cards can be placed at any point of a player's turn</li>
                <li>Reverse cards reverse the turn order</li>
                <li>Skip cards skip the next player</li>
                <li>+1 cards forces the next player to draw one card</li>
                <li>+2 Wild cards allow the player to change the color and forces the next player to draw two extra cards</li>
                <li>Flip cards forces all players to flip their hand</li>
                <li>Skip Everyone cards skips allow the current player to take another turn</li>
                <li>+5 cards forces the next player to draw five card</li>
                <li>Wild draw color forces the next player to draw cards until the color matches the one chosen</li>
                <li>If the player has one card remaining, they must say "Uno" or they must draw 2 cards if an opponent says it first</li>
                <li>If a player loses all cards, the game ends</li>`
                break;

            case 'expoldingkittens':

                document.body.style.color = "white";
                document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
                localStorage.setItem("buttonValue", 'expoldingkittens');
                
                document.getElementById("topic").innerHTML = "Exploding Kittens"
                document.getElementById("unordered").style.borderColor = "green";
                document.getElementById("unordered").innerHTML = `<li>Each player is given a hand of eight cards: seven random and one defuse</li>
                <li>Avoid drawing a exploding kitten from the draw pile to prevent elimination</li>
                <li>If you draw an exploding kitten, a defuse can prevent elimination but you lose the defuse</li>
                <li>Drawing an exploding kitten will allow the player to place it into the draw pile wherever they choose</li>
                <li>Utilize unique cards to manipulate the draw pile, sabotage other players, and skip your turn</li>
                <li>Cat cards can be matched together to perform specific actions</li>
                <li>Collecting two cat cards allow the current player to choose a card from another player's hand at random</li>
                <li>Collecting three cat cards allow the current player say what card they want from another player's hand</li>
                <li>Five different cards allow the current player to pick a card of their choice from the discard pile</li>
                <li>Feral cats can be paired with any cat card and act as a matching pair</li>
                <li>Skip cards skip the current player's turn</li>
                <li>Attack cards forces the next player to draw two cards</li>
                <li>See the future cards allows the current player to view the top three cards in the draw pile</li>
                <li>Alter the future allows the current player to view and manipuate the top three cards in the draw pile</li>
                <li>Shuffle cards allow the current player to shuffle the deck</li>
                <li>Draw from the bottom card allows the current player to select the card at the bottom for their turn</li>
                <li>Favor cards forces another player to give you a card of their choice</li>
                <li>Nope cards can be played at any time to stop an action of another player or yourself or negate a nope card</li>`
                break;

            default:
                document.body.style.color = "white"; 
            }
        };

        radioClicked();
        </script>
        

        <div class="HeaderStyle">Instructions: ${user}</div>
        
        <div class="BackButton">
            <a href="../gamerepo/home"><button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
    </div>

    <h2 id="topic" >Blackjack</h2>
    
    <div id="backing">
	    <ul id="unordered">
	        <li>The goal of blackjack is for the player to tally points up to 21</li>
	        <li>The player is given a choice to "Hit," "Freeze," or "Hold."</li>
	        <li>Choosing "Hold" makes the player skip their turn and wait until the turn-order comes back around</li>
	        <li>Choosing "Hit" makes the player take a card from the deck</li>
	        <li>You will be given a card from a 52-standard card deck</li>
	        <li>Each card equals thier value but Kings, Queens, and Jacks are 10 points</li>
	        <li>Aces allow the user to choose between the card's values of 11 or 1</li>
	        <li>If the player goes over this limit of 21, they are removed from the game</li>
	        <li>No other player will know the amount of points you have or what card you have</li>
	    </ul>
    </div>

    <div id = "bootybutt" onload="radioClicked()" onclick="radioClicked()">
        
        <label class = "buttspace">
            <input type="radio" name="butt" value="blackjack" onclick="">
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