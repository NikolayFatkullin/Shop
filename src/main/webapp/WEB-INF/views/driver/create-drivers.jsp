<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create driver</title>
</head>
<body>
<h1>Please enter data about driver</h1>

<form method="post" action="${pageContext.request.contextPath}/driver/create-drivers">
    Please provide driver name<input type="text" name="name">
    Please provide driver license number<input type="text" name="licenseNumber">

    <button type="submit">Create</button>
</form>
</body>
</html>
