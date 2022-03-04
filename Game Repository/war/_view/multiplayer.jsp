<!DOCTYPE html>
<html>
    <head>
        <style>

        body {
            font-family: 'Ubuntu', sans-serif;
        }

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
            <a href="http://localhost:8081/gamerepo/home"><button class="right" type="submit">Main Menu</button>
            </a>
    </div>

        <div>Multiplayer: &nbsp; <input type = "text" placeholder = "Username"></div>

    <h2 id="topic"><u>Card Game Desired</u></h2>
    
    <a href="http://localhost:8081/gamerepo/host"><button class="branch" type="submit">Host</button><br>
    </a>
    
    <a href="http://localhost:8081/gamerepo/host"><button class="branch" type="submit">Join</button>
    </a>

    </body>
</html>