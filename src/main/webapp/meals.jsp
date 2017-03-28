<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1px solid" align="left">
    <tr style="font-weight: bold">
        <td>Date</td>
        <td>Time</td>
        <td>Description</td>
        <td>Calories</td>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <tr style="color: ${meal.exceed ? "red" : "green"}" id="meal">
            <td>${meal.dateTime.toLocalDate()}</td>
            <td>${meal.dateTime.toLocalTime()}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
