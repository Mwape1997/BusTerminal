<%@ page import="at.ac.fhcampuswien.modal.LadePersonal" %>
<%@ page import="at.ac.fhcampuswien.bean.LoginBean" %>
<%@ page import="at.ac.fhcampuswien.modal.Angestellter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    /*Disable the back button after a logout*/
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setHeader ("Expires", "0");

    LoginBean loginBean = (LoginBean) session.getAttribute("userSession");
    if (loginBean == null){
        session.setAttribute("loginMsg", "You need to login first!");
        response.sendRedirect("index.jsp");
        return;
    }else if (loginBean.getAngestelltennummer() == null){
        session.setAttribute("loginMsg", "You need to login first!");
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    //LoginBean loginBean = (LoginBean) session.getAttribute("userSession");
    if("POST".equalsIgnoreCase(request.getMethod())) {
        if (request.getParameter("reqErmKarte").equals("Request Card")) {
            Angestellter angestellter = new Angestellter(loginBean);

            if(angestellter.reqErmKarte()){
                session.setAttribute("request", "Karte wurde erfolgreich reserviert!");
            } else {
                session.setAttribute("request", "Karte konnte nicht reserviert werden!");
            }
            if (loginBean.getLicenseNumber() != null){
                if (!loginBean.getLicenseNumber().isEmpty())
                    response.sendRedirect("loadingPersonal.jsp");
            }else
                response.sendRedirect("schalterbediensteter.jsp");
        }
    }

%>
</body>
</html>
