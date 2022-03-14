<!DOCTYPE html>
<html>
    <head>
    	<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/host.css"/>
    
        <title>
            Hosting
        </title>
    </head>
    <body class="StaticBackground">


      <div id="left"><a href="http://localhost:8080/gamerepo/hostextend"><button class="ButtonStyle" type="submit">Host</button> 
      </a>
    </div>
    <div class="BackButton">
      <a href="http://localhost:8080/gamerepo/home"><button class="ButtonStyle" type="submit">Main Menu</button>
      </a>
    </div>

    <h2 id="topic"><u>Card Game Desired</u></h2>
    Difficulty Level:
    <div class="difficulty-slider">
        <input id="difficultyEasy" type="radio" name="difficulty">
        <label for="difficultyEasy">Easy</label>
        <input id="difficultyEasy" type="radio" name="difficulty">
        <label for="difficultyEasy">&nbsp;</label>
        <input id="difficultyMedium" type="radio" name="difficulty" checked>
        <label for="difficultyMedium">Medium</label>
        <input id="difficultyMedium" type="radio" name="difficulty">
        <label for="difficultyMedium">&nbsp;</label>
        <input id="difficultyHard" type="radio" name="difficulty">
        <label for="difficultyHard">Hard</label>
        <input id="difficultyHard" type="radio" name="difficulty">
        <label for="difficultyHard">&nbsp;</label>
      </div>

      Bot Count:
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

        <label class="ckb"> Doubling: &nbsp;
            <input type="checkbox" name="ckb1" value="a" unchecked>
            <i></i>
          </label>
          <br><br>

          Player Count:
          <div class="player-slider">
            <input id="player1" type="radio" name="player">
            <label for="player1">1</label>
            <input id="player2" type="radio" name="player">
            <label for="player2">2</label>
            <input id="player3" type="radio" name="player" checked>
            <label for="player3">3</label>
            <input id="player4" type="radio" name="player">
            <label for="player4">4</label>
            <input id="player5" type="radio" name="player">
            <label for="player5">5</label>
          </div>

    </body>
</html>