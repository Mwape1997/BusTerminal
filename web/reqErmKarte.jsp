<%@ page import="at.ac.fhcampuswien.modal.LadePersonal" %>
<%@ page import="at.ac.fhcampuswien.bean.LoginBean" %><%--
  Created by IntelliJ IDEA.
  User: jakob
  Date: 19.06.2020
  Time: 17:06
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
        if (request.getParameter("reqErmKarte").equals("reqErmKarte")) {
            LadePersonal ladePersonal = new LadePersonal(loginBean);

            if(ladePersonal.reqErmKarte()){
                out.print("Erfolgreich reserviert");
            } else {
                out.print("Konnte nicht reservieren");
            }
            response.sendRedirect("loadingPersonal.jsp");
        }
    }

%>
</body>
</html>
