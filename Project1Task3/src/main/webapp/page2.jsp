<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Baseball App</title>
</head>
<body>
<h1><%= "Welcome to MLB Baseball App!" %>
</h1>


<form action="getTeam" method="GET">

    <% if (request.getAttribute("teamStats") != null) { %>
    <p> Data Scraped from: https://www.cbssports.com/mlb/news/2023-mlb-playoff-picture-baseball-standings-postseason-projections-tiebreakers-magic-numbers/</p>
    <p> Images scraped from: </p>
    <p> Website 1: https://www.cbssports.com/mlb/standings/
        Website 2: "https://news.sportslogos.net/2013/05/16/mlb-updates-both-al-and-nl-league-logos/baseball/"</p>
    <p> <%= request.getAttribute("teamStats") %> </p>
    <% } %>
    <p></p>
    <p>Search for a player to view their stats</p>
    <p></p>
    <input type="text" name="searchWord" value="" /><br>
    <p></p>
    <input type="submit" value="Submit" />
</form>
<!Reference: https://www.w3schools.com/html/html_forms.asp>
<!Reference: Interesting Picture - Lab 2 (result.jsp)>
<br/>

</body>
</html>