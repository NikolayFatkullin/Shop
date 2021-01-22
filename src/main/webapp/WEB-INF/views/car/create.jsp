<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Please enter data about car</h1>

<form method="post" action="${pageContext.request.contextPath}/car/create">
    Please provide car model<input type="text" name="model">
    Please provide car manufacturer id<input type="number" name="manufacturer_id">

    <button type="submit">Create</button>
</form>
</body>
</html>
