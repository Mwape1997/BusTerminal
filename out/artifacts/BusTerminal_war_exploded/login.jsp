<%@ page import="at.ac.fhcampuswien.modal.LoginModal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<jsp:useBean id="objLoginBean" class="at.ac.fhcampuswien.bean.LoginBean"/>
<jsp:setProperty name="objLoginBean" property="*"/>

<%
    if("POST".equalsIgnoreCase(request.getMethod())){
        if (request.getParameter("login").equals("Login")){

            LoginModal loginModal = new LoginModal();
            boolean successful = loginModal.checkCredentials(objLoginBean);

            if (successful) {
                //session = request.getSession();
                if (objLoginBean.getCustomerNumber() != null){
                    session.setAttribute("userSession", objLoginBean);
                    response.sendRedirect("contactPerson.jsp");
                }
                else if (objLoginBean.getAngestelltennummer() != null){
                    /*entweder schalterbediensteter oder ladepersonal*/
                    if (loginModal.isSchalterbediensteter(objLoginBean)){
                        session.setAttribute("userSession", objLoginBean);
                        response.sendRedirect("schalterbediensteter.jsp");
                    }else {
                        String licenseNumber = loginModal.retrieveLicenseNumber(objLoginBean);
                        objLoginBean.setLicenseNumber(licenseNumber);
                        session.setAttribute("userSession", objLoginBean);
                        response.sendRedirect("loadingPersonal.jsp");
                    }
                }
            }
            else {
                response.sendRedirect("index.jsp?status=false");
            }
        }
    }
%>

</body>
</html>
