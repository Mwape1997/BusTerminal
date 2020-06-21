<%@ page import="at.ac.fhcampuswien.bean.LoginBean" %>
<%@ page import="at.ac.fhcampuswien.modal.LadePersonal" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    /*Disable the back button after a logout*/
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setHeader ("Expires", "0");

    session = request.getSession();
    LoginBean loginBean = (LoginBean) session.getAttribute("userSession");
    String svnr="";
    String birthDate="";
    String userPosition="";
    if (loginBean == null){
        response.sendRedirect("index.jsp");
        session.setAttribute("loginMsg", "You need to login first!");
    }
    else{
        svnr = loginBean.getSvnr();
        birthDate = loginBean.getBirthDate();
        userPosition = loginBean.getUserPosition();
    }
%>

<html>
<head>
    <title>LoadingPersonal</title>
</head>
<body>
<jsp:useBean id="objLoginBean" class="at.ac.fhcampuswien.bean.LoginBean"/>
<jsp:setProperty name="objLoginBean" property="*"/>
<div about="Logout Button" align="right"><a href="logout.jsp">Logout</a></div>

<div about="View of Ermäßigungskarte" align="left">
    <%
        String result;
        LadePersonal ladePersonal = new LadePersonal(loginBean);
        //LoginBean loginBean = (LoginBean) session.getAttribute("userSession");
        //out.print("hier" + loginBean.getLicenseNumber().toString());
        //out.print("Lizenznummer" + objLoginBean.getLicenseNumber().toString());
        result = ladePersonal.showErmKarte();

        if(result==null){
            out.print("<p>Sie besitzen noch keine Ermäßigungskarte!</p>\n" +
                    "<form name=\"reqErmKarte\" method=\"post\" action=\"reqErmKarte.jsp\">\n" +
                    "        <input type=\"submit\" name=\"reqErmKarte\" value=\"reqErmKarte\"/>\n" +
                    "    </form>");
            /*if(ladePersonal.reqErmKarte()){
                out.print("Erfolgreich reserviert");
            }else {
                out.print("Konnte Keine Karte reservieren");
            }*/
        }
        else {
            out.print("<p>Ermäßigungskartennummer: " + result + "</p>");
        }
    %>
</div>
<br>
<br>
<div about="View of Terminal">
    <table style="width:50%" border="2px">
        <tr>
            <th>Terminal</th>
            <th>Anzahl</th>
            <th>Kapazität</th>
        </tr>
        <tr>
    <%
        ArrayList<String> terminalList;
        terminalList = ladePersonal.getTerminalOverwiev();
        int a = 0;

        for(int i=0; i<terminalList.size(); i++){
            out.print("<td>" + terminalList.get(i) + "</td>");
            if((i+1)%3==0){
                out.print("</tr><tr>");
            }
        }

        /*terminalList.forEach(value -> {

                System.out.print("<td>" + value + "</td>");

        });*/
    %>
        </tr>
    </table>
</div>
</body>
</html>
