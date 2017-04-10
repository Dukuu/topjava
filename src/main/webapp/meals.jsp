<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }

        .div1 {
            display: block;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <form action="meals" method="get">
        <div style="display: inline">
            <div id="dateSort" style="display: inline-block">
                <div class="div1">
                    <label>
                        От даты: <input type="date" name="dateSortFrom" />
                    </label>
                </div>
                <div class="div1">
                    <label>
                        До даты: <input type="date" name="dateSortTo" />
                    </label>
                </div>
            </div>
            <div id="timesSort" style="display: inline-block">
                <div class="div1">
                    <label>
                        От времени: <input type="time" name="timeSortFrom" />
                    </label>
                </div>
                <div class="div1">
                    <label>
                        До времени: <input type="time" name="timeSortTo" />
                    </label>
                </div>
            </div>
        </div>
        <br>
        <div>
            <input type="submit" value="Sort">
            <input type="reset" value="Reset">
            <%--<button type="button" onclick="for (var i=0; i<document.getElementsByTagName('input').length; i++) {
                document.getElementsByTagName('input')[i].value = document.getElementsByTagName('input')[i].defaultValue;
            }">Reset</button>--%>
        </div>
    </form>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>