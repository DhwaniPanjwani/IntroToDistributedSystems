
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>JSP - Baseball App</title>
</head>
<body>
<h1><%= "Welcome to MLB Baseball App!" %>
</h1>
<form action="getPlayers" method="GET">
    <% if (request.getAttribute("newsPlayerName") != null) { %>
    <p> API CALL: https://appac.github.io/mlb-data-api-docs/ </p>
    <!/json/named.transaction_all.bam?sport_code='mlb'&start_date={start_date}&end_date={end_date}>
    <p> </p>

    <% if (request.getAttribute("newsData") != null) { %>
    <p> <b>Player Name:</b> </p>
    <p> <%= request.getAttribute("newsPlayerName") %> </p>
    <p> <b> Player Related Recent News: </b></p>
    <p> <%= request.getAttribute("newsData") %> </p>
    <% } %>
    <% } %>

    <!Reference: https://www.w3schools.com/html/html_forms.asp>
    <!Reference: Interesting Picture - Lab 2 (result.jsp)>
</form>
</body>
</html>
