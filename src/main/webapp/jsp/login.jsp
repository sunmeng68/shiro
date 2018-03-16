<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/5
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h4>Login Page</h4>


<form action="${pageContext.request.contextPath}/shiro/login" method="post">
    username:<input type="text" name="username">
    <br><br>
    password:<input type="text" name="password">

    <input type="submit" value="Submit">

</form>

</body>
</html>
