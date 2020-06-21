<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    session.removeAttribute("userSession");
    session.invalidate();
    response.sendRedirect("index.jsp");
%>

<html>
<head>
    <title>Logout</title>
</head>
<body>

</body>
</html>
