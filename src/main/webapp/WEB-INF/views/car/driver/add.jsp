<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Please enter data car and driver</h1>

<form method="post" action="${pageContext.request.contextPath}/car/driver/add">
    Please provide car id<input type="number" name="car_id">
    Please provide driver id<input type="number" name="driver_id">

    <button type="submit">Create</button>
</form>
</body>
</html>
