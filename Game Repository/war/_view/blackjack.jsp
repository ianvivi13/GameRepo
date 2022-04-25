<!DOCTYPE html>
<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/blackjack.css"/>

        <title>
            BlackJack
        </title>
    </head>
    <body class="BlackJack">
        <script>
            document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";
            document.body.style.color = "black";
        </script>
        <div id="left"><a href="http://localhost:8080/gamerepo/home"><button class="ButtonStyle" type="submit">Exit</button> 
        </a>
    </div>
    <div id="deck">
        Deck
    </div>
    <div id="players">
        <div class="split" id="player1">
            <div class="split" id="playermain">
                <div id="center">Main Hand: ${user}</div>
                

                <button class="ButtonStyle" id="Hit" type="submit" onClick="hit(game)" value="Hit">Hit</button> 
            </div>

            <div class="split" id="playeralt">
                <div id="center">Alt Hand: ${user}</div>
                
                <form>
                    <button class="ButtonStyle" id="blend" type="submit" onClick="hold(game)" value="Hold">Hold</button> 
                <button class="ButtonStyle" id="blend" type="submit" onClick="freeze(game)" value="Freeze">Freeze</button> 
                </form>
            </div>
        </div>
        <div class="split" id="player2">
            <div class="split" id="playermain">
                <div id="center">
                    Main Hand: 
                </div>
            </div>

            <div class="split" id="playeralt">
                <div id="center">
                    Alternate Hand: 
                </div>
            </div>
        </div>
    </div>


    </body>

</html>