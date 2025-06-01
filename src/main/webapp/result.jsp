<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Результат</title></head>
<style>
    body {
        background-image: url('/images/alien.jpg');
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
<h2>
    <c:choose>
        <c:when test="${status == 'WINS'}">🎉 Вы победили!</c:when>
        <c:otherwise>💀 Игра окончена.</c:otherwise>
    </c:choose>
</h2>

<form method="post" action="${pageContext.request.contextPath}/restart">
    <button type="submit">Начать заново</button>
</form>
</body>
</html>
