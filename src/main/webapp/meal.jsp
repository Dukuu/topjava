<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="meals" name="addMealForm">

    Meal ID: <input type="text" readonly="readonly" name="id"
    value="<c:out value="${meal.id}"/>" /> <br/>

    Meal date: <input type="date" name="date" required="required"
    value="<c:out value="${meal.getDate()}"/>" /> <br/>

    Meal time: <input type="time" name="time" required="required"
    value="<c:out value="${meal.getTime()}"/>" /> <br/>

    Meal description: <input type="text" name="descr" required="required"
    value="<c:out value="${meal.description}"/>" /> <br/>

    Meal calories: <input type="text" name="cal" required="required"
    value="<c:out value="${meal.calories}"/>" /> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>
