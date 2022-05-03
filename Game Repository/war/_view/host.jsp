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
    	<div class="HeaderStyle">${user}</div>
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
          //document.getElementById("topic").innerHTML = 'Exploding Kittens';
          console.log(document.getElementById("topic").innerHTML)
  
					localStorage.setItem("buttonValue", 'expoldingkittens');
					console.log("buttonValue");
					break;

				default:

          document.body.style.color = "white";
          document.body.style.backgroundImage = "url('_view/css/Back.png')";
				}
      </script>
      
    
    <div class="BackButton">
      <a href="../gamerepo/home"><button class="ButtonStyle" type="submit">Main Menu</button>
      </a>
    </div>

          <br><br><br><br>

          <div class="topics">
    <span  style="font-size: 150%; color: #ffffffff; background-color: #3b3b3bb4; border-radius: 13px; ">Set Player Count</span>
          <br><br>
          	<div>
          		<form method="post">
	          		<select style="text-align: center; font-size: 180%; border-radius: 13px; width: 30%; background-color: #3b3b3b; color: #ffffff; border-color: #000000;">
	          			<%
	          			int t = 2;
	          			switch ((String) session.getAttribute("happy")) {
	          				case ("uno"): t = 4; break;
	          				case ("unoflip"): t = 4; break;
	          				case ("expoldingkittens"): t = 6; break;
	          			}
	          			%>
	          			<% for (int i = 2 ; i <= t ; i ++) { %>
							<option style="" value = <% out.print(i); %>> <% out.print(i); %> </option>  
						<% } %> 
					</select> 
					<br><br>
			    	<div id=""><button class="ButtonStyle" name="play" value="play" type="submit">Host Lobby</button> </div>
			    </form> 
          	</div>
          </div>
    </body>
</html>