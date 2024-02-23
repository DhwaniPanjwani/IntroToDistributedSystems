
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>JSP - Baseball App</title>
</head>
<body>
<h1><%= "Welcome to MLB Baseball App!" %>
</h1>

<form action="getPlayers" method="GET">
    <% if (request.getAttribute("playerStats") != null) { %>
    <p> Scraped from "https://legacy.baseballprospectus.com/pitchfx/leaderboards/"</p>
    <p><b> Here are the player statistics </b></p>
    <p><b>Player               Tm Th Num Velo H Mov V Mov Sw Rate Whf/Sw Foul/Sw GB/FB GB% LD% FB% PU% CS/Tk CS%</b></p>
    <p><%= request.getAttribute("playerStats") %></p>
    <% } else { %>
    <p>No results found</p>
    <% } %>
    <p> <b> Search for a player to view their recent news </b></p>
    <input type="text" name="newsPlayerName" value="" /><br>
    <p></p>
    <input type="submit" value="Submit" />

    <!Reference: https://www.w3schools.com/html/html_forms.asp>
    <!Reference: Interesting Picture - Lab 2 (result.jsp)>
</form>
</body>
</html>
