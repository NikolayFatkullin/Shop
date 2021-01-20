<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<h1>Please enter data car and driver</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add-drivers-to-car">
    Please provide car id<input type="number" name="carId">
    Please provide driver id<input type="number" name="driverId">

    <button type="submit">Create</button>
</form>
</body>
</html>
