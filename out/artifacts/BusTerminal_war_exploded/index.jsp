<%@ page import="at.ac.fhcampuswien.bean.LoginBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="profile/css/styling.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        /*Function to handle the hidden fields of the radio buttons (Customer Number, License Number)*/
        function handleClick(clickedId)
        {
            if(clickedId === "contact"){
                document.getElementById('customerNumber').required = true;
                document.getElementById('licenseNumber').required = false;
            }
            else if(clickedId === "loading"){
                document.getElementById('licenseNumber').required = true;
                document.getElementById('customerNumber').required = false;
            }
        }
    </script>
</head>
<body>

<%
    /*To prevent the user from getting back to the login form after logging in*/
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setHeader ("Expires", "0"); //prevents caching at the proxy server

    LoginBean loginBean = (LoginBean) session.getAttribute("userSession");
    if (loginBean != null){
        String svnr = loginBean.getSvnr();
        String birthDate = loginBean.getBirthDate();
        String userPosition = loginBean.getUserPosition();
        //redirect user to the corresponding page if already logged in
        if(svnr != null && birthDate != null && userPosition != null){
            if (userPosition.equals("contactPerson"))
                response.sendRedirect("contactPerson.jsp");
            else if (userPosition.equals("loadingPersonal"))
                response.sendRedirect("loadingPersonal.jsp");
        }
    }
    String status = request.getParameter("status");
    if(status!=null){
        if(status.equals("false")){
            session.setAttribute("loginMsg", "Incorrect Login Credentials");
        }
        else{
            session.setAttribute("loginMsg", "Some error has occurred!");
        }
    }
%>

<div class="wrapper" style="text-align: center;">
    <h3 class="form-signin-heading">Login Here</h3>
    <form class="form-signin" action="login.jsp" method="post">
        <label for="svnr">Enter Your SVNR</label><span style="color: red;">*</span>
        <input id="svnr" name="svnr" type="text" maxlength="4" pattern="\d{4}" required/>
        <br/><br/>
        <label for="birthDate">Enter Your Birth Date</label><span style="color: red;">*</span>
        <input id="birthDate" name="birthDate" type="date" required/>
        <br/><br/>
        <div>
            <label for="contact">Contact Person</label>
            <input id="contact" type="radio" name="userPosition" value="contactPerson" onclick="handleClick(this.id)">
            <div class="reveal_if_contactPerson details">
                <label for="customerNumber">Customer Number:</label><span style="color: red;">*</span>
                <input id="customerNumber" type="text" name="customerNumber">
                <br>
            </div>
        </div>
        <div>
            <label for="loading">Loading Personal</label>
            <input id="loading" type="radio" name="userPosition" value="loadingPersonal" onclick="handleClick(this.id)" required>
            <div class="reveal_if_loadingPersonal details">
                <label for="licenseNumber">License Number:</label><span style="color: red;">*</span>
                <input id="licenseNumber" type="text" name="licenseNumber">
                <br>
            </div>
        </div>
        <input name="login" type="submit" value="Login"/>
    </form>
    <%
        String loginMsg = (String) session.getAttribute("loginMsg");
        if (loginMsg != null){
            out.println(loginMsg);
            session.removeAttribute("loginMsg");
        }
    %>
</div>
</body>
</html>
