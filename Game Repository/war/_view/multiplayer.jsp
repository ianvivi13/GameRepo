<!DOCTYPE html>
<html>
    <head>
    	<link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@300&display=swap" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="_view/css/MasterStyles.css"/>
        <link rel="stylesheet" type="text/css" href="_view/css/multiplayer.css"/>
        <style>

        .subtopic {
            font-size: 50px;
            text-align: center;
            margin: 5px;
        }

        #topic {
            text-align: center;
            margin-top: 40px; 
        }

        #right {
            float: right;
            clear: both;
        }

        button.right {
            width: 200px;
            
            padding: 12px 10px;
            font-size: 18px;
            color: white;
            border-radius: 15px;
            background-color: #3b3b3b;
        }

        button.branch {
            width: 250px;
            height: 100px;
            padding: 12px 10px;
            font-size: 18px;
            color: white;
            border-radius: 15px;
            background-color: #3b3b3b;
        
            margin-top: 4%;
            margin-left: 42%;
    }

        </style>
        <title>
            Multiplayer
        </title>
    </head>
    <body>
        
        <div id="right">
            <a href="http://localhost:8080/gamerepo/home"><button class="right" type="submit">Main Menu</button>
            </a>
    </div>

        <div>Multiplayer: &nbsp; <input type = "text" placeholder = "Username"></div>

    <h2 id="topic"><u>Card Game Desired</u></h2>
    
    <a href="http://localhost:8080/gamerepo/host"><button class="branch" type="submit">Host</button><br>
    </a>
    
    <a href="http://localhost:8080/gamerepo/join"><button class="branch" type="submit">Join</button>
    </a>

    </body>
</html>