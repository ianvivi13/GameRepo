<!DOCTYPE html>

<html>
	<head>
		<title>Stats</title>
		<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/statpage.css"/>
	</head>
	
	<body class="StaticBackground">
        <script>
        let value = localStorage.getItem("buttonValue");

        switch (value) {
        case 'blackjack':
            
            document.body.style.color = "black";
            document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

            localStorage.setItem("buttonValue", 'blackjack');
            console.log("buttonValue");

            document.getElementById("stats").innerHTML = 
            `<span id = "tit"><h3>Blackjack Stats</h3></span>
            <p>Games Played:&nbsp; 12</p>
            <p>Games Won:&nbsp; 6</p>
            <p>Games Loss:&nbsp; 6</p>
            <p>Win/Loss Ratio:&nbsp; 0.50</p>  `
            break;

        case 'uno':
                
            document.body.style.color = "black";
            document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";
            localStorage.setItem("buttonValue", 'uno');
            console.log("buttonValue");

            document.getElementById("stats").innerHTML = 
            `<span id = "tit"><h3>Uno Stats</h3></span>
            <p>Games Played:&nbsp; 33</p>
            <p>Games Won:&nbsp; 11</p>
            <p>Games Loss:&nbsp; 22</p>
            <p>Win/Loss Ratio:&nbsp; 0.33</p>  `
            break;    

        case 'unoflip':
                
            document.body.style.color = "white";
            document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
            localStorage.setItem("buttonValue", 'unoflip');
            console.log("buttonValue");

            document.getElementById("stats").innerHTML = 
            `<span id = "tit"><h3>Uno-Flip Stats</h3></span>
            <p>Games Played:&nbsp; 45</p>
            <p>Games Won:&nbsp; 40</p>
            <p>Games Loss:&nbsp; 5</p>
            <p>Win/Loss Ratio:&nbsp; 9.0</p>  `
            break;

        case 'expoldingkittens':

            document.body.style.color = "white";
            document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
            localStorage.setItem("buttonValue", 'expoldingkittens');
            console.log("buttonValue");

            document.getElementById("stats").innerHTML = 
            `<span id = "tit"><h3>Exploding Kittens Stats</h3></span>
            <p>Games Played:&nbsp; 10</p>
            <p>Games Won:&nbsp; 1</p>
            <p>Games Loss:&nbsp; 9</p>
            <p>Win/Loss Ratio:&nbsp; 0.11</p>  `
            break;

        default:
            document.body.style.color = "white";
            document.body.style.backgroundImage = "url('_view/Back.png')";
            
    }

        function radioClicked(){
            let shapeChoice = document.querySelector('input[name="butt"]:checked').value;

            switch (shapeChoice) {
            case 'blackjack':

                document.body.style.color = "black";
                localStorage.setItem("buttonValue", 'blackjack');
                console.log("buttonValue");
            
                document.body.style.backgroundImage = "url('_view/images/BlackJack_Back.jpg')";

                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Blackjack Stats</h3></span>
                <p>Games Played:&nbsp; 12</p>
                <p>Games Won:&nbsp; 6</p>
                <p>Games Loss:&nbsp; 6</p>
                <p>Win/Loss Ratio:&nbsp; 0.50</p>  `
                break;

            case 'uno':

                document.body.style.color = "black";
                localStorage.setItem("buttonValue", 'uno');
                console.log("buttonValue");
                
                document.body.style.backgroundImage = "url('_view/images/Uno_Back.jpg')";    

                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Uno Stats</h3></span>
                <p>Games Played:&nbsp; 33</p>
                <p>Games Won:&nbsp; 11</p>
                <p>Games Loss:&nbsp; 22</p>
                <p>Win/Loss Ratio:&nbsp; 0.33</p>  `
                break;

            case 'unoflip':

                document.body.style.color = "white";
                localStorage.setItem("buttonValue", 'unoflip');
                console.log("buttonValue");
                
                document.body.style.backgroundImage = "url('_view/images/UnoFlip_Back.jpg')";
                
                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Uno-Flip Stats</h3></span>
                <p>Games Played:&nbsp; 45</p>
                <p>Games Won:&nbsp; 40</p>
                <p>Games Loss:&nbsp; 5</p>
                <p>Win/Loss Ratio:&nbsp; 9.0</p>  `
                break;

            case 'expoldingkittens':

                document.body.style.color = "white";
                localStorage.setItem("buttonValue", 'expoldingkittens');
                console.log("buttonValue");

                document.body.style.backgroundImage = "url('_view/images/Exploding_Back.jpg')";
                
                document.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Exploding Kittens Stats</h3></span>
                <p>Games Played:&nbsp; 10</p>
                <p>Games Won:&nbsp; 1</p>
                <p>Games Loss:&nbsp; 9</p>
                <p>Win/Loss Ratio:&nbsp; 0.11</p>  `
                break;

            default:

                document.body.style.color = "white";
                doucment.getElementById("stats").innerHTML = 
                `<span id = "tit"><h3>Global Stats</h3></span>
                <p>Games Played:&nbsp; 100</p>
                <p>Games Won:&nbsp; 40</p>
                <p>Games Loss:&nbsp; 60</p>
                <p>Win/Loss Ratio:&nbsp; 0.67</p `
            }
        };

                radioClicked();
        </script>


        <div class = "HeaderStyle">
            Stats: ${user}
        </div>
    
        <div class = "BackButton">
            <a href="http://localhost:8080/gamerepo/home">
                <button class="ButtonStyle" type="submit">Main Menu</button>
            </a>
        </div>    

        <div id = "glob">
            <div id = "globalrank">
                <span id = "tit"><h3>Global Rank</h3></span>
                <span id = "text"><p>8</p></span>
            </div>
    
            <div id = "stats">
                <span id = "tit"><h3>Global Stats</h3></span>
                <p>Games Played:&nbsp; 100</p>
                <p>Games Won:&nbsp; 40</p>
                <p>Games Loss:&nbsp; 60</p>
                <p>Win/Loss Ratio:&nbsp; 0.67</p>                   
            </div>
        </div>

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