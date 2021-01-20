<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Please enter data about manufacturer</h1>

<form method="post" action="${pageContext.request.contextPath}/manufacturer/create-manufacturers">
    Please provide manufacturer name<input type="text" name="name">
    Please provide manufacturer country<input type="text" name="country">

    <button type="submit">Create</button>
</form>
</body>
</html>
