<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Baseball App</title>
</head>
<body>
<h1><%= "Welcome to MLB Baseball App!" %>
</h1>

<p> Choose the League to view their statistics: </p>
<form action="getOption" method="GET">

    <input type="radio" id="NL" name="optionChoice" value="NL">
    <label for="NL">National League</label><br>
    <input type="radio" id="AL" name="optionChoice" value="AL">
    <label for="AL">American League</label><br>
    <p></p>

    <input type="submit"  value="Submit">
    <!Reference: https://www.w3schools.com/html/html_forms.asp>
    <!Reference: Interesting Picture - Lab 2 (result.jsp)>



</form>


<br/>
</body>
</html>