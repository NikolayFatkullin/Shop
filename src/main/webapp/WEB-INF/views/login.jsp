<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>
<h4 style="color: red">${error_message}</h4>

<form action="${pageContext.request.contextPath}/login" method="post">
    Please provide your login<input type="text" name="login">
    Please provide your password<input type="number" name="password">

    <button type="submit">Login</button>
</form>
<a href="${pageContext.request.contextPath}/drivers/create">Registration</a>
</body>
</html>
