<%@ page import="at.ac.fhcampuswien.bean.LoginBean" %>
<%@ page import="at.ac.fhcampuswien.modal.LadePersonal" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="at.ac.fhcampuswien.modal.Schalterbediensteter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    /*Disable the back button after a logout*/
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    //session = request.getSession();
    LoginBean loginBean = (LoginBean) session.getAttribute("userSession");
    if (loginBean != null) {
        if (loginBean.getAngestelltennummer() != null) {
            if (loginBean.getLicenseNumber() != null)// Angestellter hat keine LN ==> Schalterbediensteter
                response.sendRedirect("loadingPersonal.jsp");
        } else
            response.sendRedirect("logout.jsp");
    } else {
        session.setAttribute("loginMsg", "You need to login first!");
        response.sendRedirect("index.jsp");
        return;
    }

%>


<html>
<head>
    <title>Schalterbediensteter</title>
    <script type="text/javascript">
        /*Function to handle the hidden fields of the radio buttons (Customer Number, License Number)*/
        function handleClick(clickedId)
        {
            if(clickedId === "requestBtn"){
                document.getElementById('requestDiv').style.visibility = "hidden";
                document.getElementById('releaseDiv').style.visibility = "visible";
            }
            else if(clickedId === "releaseBtn"){
                document.getElementById('releaseDiv').style.visibility = "hidden";
                document.getElementById('requestDiv').style.visibility = "visible";
            }
        }
    </script>
</head>
<body>

<div about="Logout Button" align="right"><a href="logout.jsp">Logout</a></div>

<div about="Personal Data">
    <h3>Persönliche Daten</h3>
    <table style="width:25%" border="1px">
        <tr>
            <th>Vorname</th>
            <th>Nachname</th>
            <th>Wohnort</th>
        </tr>
        <tr>
            <%
                Schalterbediensteter schalterbediensteter = new Schalterbediensteter(loginBean);
                ArrayList<String> sqlResultList = schalterbediensteter.getPersonalData();
            %>
            <td>
                <%
                    out.print(sqlResultList.get(0));
                %>
            </td>
            <td>
                <%
                    out.print(sqlResultList.get(1));
                %>
            </td>
            <td>
                <%
                    out.print(sqlResultList.get(2));
                %>
            </td>
        </tr>
    </table>
</div>
<br>
<br>

<div about="Der nächste Schalterbediensteter">
    <h3>Sie übergeben</h3>
    <table style="width:25%" border="1px">
        <tr>
            <th>Vorname</th>
            <th>Nachname</th>
            <th>Angestelltennummer</th>
        </tr>
        <tr>
            <%
                sqlResultList = schalterbediensteter.getNextSchalterbediensteter();
            %>
            <td>
                <%
                    out.print(sqlResultList.get(0));
                %>
            </td>
            <td>
                <%
                    out.print(sqlResultList.get(1));
                %>
            </td>
            <td>
                <%
                    out.print(sqlResultList.get(2));
                %>
            </td>
        </tr>
    </table>
</div>
<br>
<br>

<div about="View of Ermäßigungskarte" align="left">
    <h3>Ihre Ermäßigungskarte</h3>
    <br>
    <%
        int result;
        result = schalterbediensteter.showErmKarte();
        if (result == 0) {
    %>
    <div id="requestDiv">
    <p>Sie besitzen noch keine Ermäßigungskarte!</p>
    <form name="reqErmKArte" method="post" action="reqErmKarte.jsp">
        <input id="requestBtn" name="reqErmKarte" type="submit" value="Request Card" onclick="handleClick(this.id)"/>
    </form>
    </div>
    <%
    } else {
    %>
    <div id="releaseDiv">
    <p>Ermäßigungskartennummer: <% out.print(result); %></p>
    <form name="relErmKarte" method="post" action="relErmKarte.jsp">
        <input id="releaseBtn" name="relErmKarte" type="submit" value="Release Card" onclick="handleClick(this.id)"/>
    </form>
    </div>
    <%
        }

        String releaseMsg = (String) session.getAttribute("release");
        String requestMsg = (String) session.getAttribute("request");
        if (releaseMsg != null){
            out.print(releaseMsg);
            session.removeAttribute("release");
        }
        if (requestMsg != null){
            out.print(requestMsg);
            session.removeAttribute("request");
        }

    %>
</div>

</body>
</html>
