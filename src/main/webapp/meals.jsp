<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table class="meals-table">
    <tr><th>Date</th><th>Description</th><th>Calories</th></tr>
        <c:forEach items="${requestScope.listofmealto}" var="mealto">
        <tr>
            <td>${mealto.dateTime}</td>
            <td>${mealto.description}</td>
            <td>${mealto.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
