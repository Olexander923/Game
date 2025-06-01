<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Квест</title></head>
<style>
    body {
        background-image: url('/images/max.jpg');
        background-size: cover;
        background-attachment: fixed;
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        align-items: center;
        color: white;
        text-shadow: 1px 1px 2px rgba(0,0,0,0.8);
    }
</style>
<body>
<h2>${question}</h2>
<form method="post">

    <c:if test="${empty options}">
        <p>Нет доступных вариантов ответа.</p>
    </c:if>
    <c:forEach items="${options}" var="option">
        <button type="submit" name="answer" value="${option}">${option}</button><br/>
    </c:forEach>


</form>
</body>
</html>
