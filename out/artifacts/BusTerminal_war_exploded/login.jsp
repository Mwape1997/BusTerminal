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

            if (objLoginBean == null)
                out.println("Object is NUL");
            else{

                LoginModal loginModal = new LoginModal();
                boolean successful = loginModal.checkCredentials(objLoginBean);

                if (successful) {
                    session = request.getSession();
                    session.setAttribute("userSession", objLoginBean); // this contains all the attributes
                    System.out.println("From Login");
                    System.out.println("Customer Number: " + objLoginBean.getCustomerNumber());
                    System.out.println("License Number: " + objLoginBean.getLicenseNumber());
                    if (objLoginBean.getCustomerNumber() != null)
                        response.sendRedirect("contactPerson.jsp");
                    else if (objLoginBean.getLicenseNumber() != null)
                        response.sendRedirect("loadingPersonal.jsp");
                }
                else {
                    response.sendRedirect("index.jsp?status=false");
                }
            }

        }
    }
%>

</body>
</html>
