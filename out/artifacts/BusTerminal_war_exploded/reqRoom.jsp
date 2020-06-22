<%@ page import="at.ac.fhcampuswien.bean.LoginBean" %>
<%@ page import="at.ac.fhcampuswien.modal.ContactPersonal" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 20.06.2020
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:useBean id="objLoginBean" class="at.ac.fhcampuswien.bean.LoginBean"/>
<jsp:setProperty name="objLoginBean" property="*"/>
<%
    session = request.getSession();
    LoginBean loginBean = (LoginBean) session.getAttribute("userSession");
    if("POST".equalsIgnoreCase(request.getMethod())) {
        if (request.getParameter("reqRoom").equals("reqRoom")) {
            ContactPersonal contactPersonal = new ContactPersonal(loginBean);

            if(contactPersonal.bookRoom()){
                out.print("Room reserved!");
            } else {
                out.print("Could not reserve Room!");
            }
            response.sendRedirect("contactPerson.jsp");
        }
    }

%>
</body>
</html>
