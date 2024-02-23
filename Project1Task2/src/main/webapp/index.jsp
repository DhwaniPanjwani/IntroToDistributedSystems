
<% if (request.getAttribute("doctype") != null) { %>
<%= request.getAttribute("doctype") %>
<% } %>

<!DOCTYPE html>
<html>
<head>
    <title>JSP - Task2</title>
</head>
<body>
<h1><%= "Distributed Systems Class Clicker" %></h1>
<% if (request.getAttribute("getTheOptionChoice") != null) { %>
<p>Your <%= request.getAttribute("getTheOptionChoice") %> has been registered</p>
<% } %>

<p><%= "Submit your answer to the current question" %>
</p>
<form action="getOption" method="GET">

    <input type="radio" id="a" name="optionChoice" value="A">
    <label for="a">A</label><br>
    <input type="radio" id="b" name="optionChoice" value="B">
    <label for="b">B</label><br>
    <input type="radio" id="c" name="optionChoice" value="C">
    <label for="c">C</label><br>
    <input type="radio" id="d" name="optionChoice" value="D">
    <label for="d">D</label><br>
    <input type="submit" value="Submit">
    <!Reference: https://www.w3schools.com/html/html_forms.asp>
    <!Reference: Interesting Picture - Lab 2 (result.jsp)>
</form>
<br/>

</body>
</html>