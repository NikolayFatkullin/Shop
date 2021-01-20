<%--
  Created by IntelliJ IDEA.
  User: achil
  Date: 20.01.2021
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Please enter data car and driver</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Please provide car id<input type="number" name="carId">
    Please provide driver id<input type="number" name="driverId">

    <button type="submit">Create</button>
</form>
</body>
</html>
