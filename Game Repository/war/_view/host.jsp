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
	          		<select name="MaxP" style="text-align: center; font-size: 180%; border-radius: 13px; width: 30%; background-color: #3b3b3b; color: #ffffff; border-color: #000000;">
	          			<%
	          			int t = 2;
	          			int j = 2;
	          			String gme = (String) session.getAttribute("happy");
	          			switch (gme) {
	          				case ("uno"): t = 4; j = 3; break;
	          				case ("unoflip"): t = 4; j = 3; break;
	          				case ("expoldingkittens"): t = 6; j = 4; break;
	          			}
	          			%>
	          			<% for (int i = j ; i <= t ; i ++) { %>
							<option name="MaxP" value = <% out.print(i); %>> <% out.print(i); %> </option>  
						<% } %>
					</select>
					<br>
					<%
					if (gme.equals("uno")) {
						%>
						<div style="background-color: #000000aa; width:20%; left: 40%; position: absolute; border-radius: 10px;">
							<input type="checkbox" name="communism" id="communism" style="color: #0f0f0f;">
							<label for="communism" style="color: #ffffff; font-size: 120%;">69 Rule</label>
							<br>
							<input type="checkbox" name="utilitarianism" id="utilitarianism" style="color: #0f0f0f;">
							<label for="utilitarianism" style="color: #ffffff; font-size: 120%;">0 Rule</label>
						</div>
						<%
					}
					%>
					<br><br><br>
			    	<div id=""><button class="ButtonStyle" name="play" value="play" type="submit">Host Lobby</button> </div>
			    </form> 
          	</div>
          </div>
    </body>
</html>