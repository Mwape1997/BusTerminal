<%@ page import="at.ac.fhcampuswien.bean.LoginBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="at.ac.fhcampuswien.modal.ContactPersonal" %>
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
    if (loginBean == null) {
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
    <title>ContactPerson</title>
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
                ContactPersonal contactPersonal = new ContactPersonal(loginBean);
                ArrayList<String> sqlResultList;
                sqlResultList = contactPersonal.getPersonalData();
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
<div about="View of Terminal">
    <h3>Ihre gebuchten Zimmer</h3>
    <table style="width:50%" border="2px">
        <tr>
            <th>Zimmernummer</th>
            <th>Buchungsdatum</th>
            <th>Beginnzeit</th>
            <th>Endzeit</th>
        </tr>
        <tr>
            <%
                sqlResultList = contactPersonal.getList();
                for (int i = 0; i < sqlResultList.size(); i++) {
                    out.print("<td>" + sqlResultList.get(i) + "</td>");
                    if ((i + 1) % 4 == 0) {
                        out.print("</tr><tr>");
                    }
                }
            %>
        </tr>
    </table>
</div>
<br>
<br>
<br>
<div>


        <table>
            <tr>
                <th>Zimmernummer</th>
                <th>Buchungsdatum</th>
                <th>Beginzeit</th>
                <th>Endzeit</th>
            </tr>
            <c:forEach var = "row" items = "${result.rows}">
                <tr>
                    <td><c:out value = "${row.Zimmernummer}"/></td>
                    <td><c:out value = "${row.Buchungsdatum}"/></td>
                    <td><c:out value = "${row.Beginnzeit}"/></td>
                    <td><c:out value = "${row.Endzeit}"/></td>
                </tr>
            </c:forEach>
        </table>


    <br>
    <h2>Zimmer Buchen</h2>


    <form method="post">

        <label for="Zimmernummer">Zimmernummer:</label>
        <input type="number" id="Zimmernummer" name="Zimmernummer" required>

        <label for="Buchungsdatum">Buchungsdatum:</label>
        <input type="date" id="Buchungsdatum" name="Buchungsdatum" min="0" required><br>

        <label for="Beginnzeit">Beginnzeit:</label>
        <input type="time" id="Beginnzeit" name="Beginnzeit" min="0"required>

        <label for="Endzeit">Endzeit:</label>
        <input type="time" id="Endzeit" name="Endzeit"><br>


        <input type="submit" value="bucht">

    </form>


    <c:if test="${not empty param.Zimmernummer && not empty param.Buchungsdatum && not empty param.Beginnzeit && not empty param.Endzeit }">



        <c:catch var="exception">


            <sql:setDataSource var = "snapshot" driver = "com.mysql.jdbc.Driver"
                               url = "jdbc:mysql://localhost:3306/"
                               user = "root"  password = ""/>
            <sql:update dataSource = "${snapshot}" var = "result">
                INSERT INTO bucht (vierstellZahl, GebDat, Zimmernummer, Buchungsnummer, Buchungsdatum, Beginnzeit, Endzeit) VALUES (?, ?, ?, ?, ?, ?, ?);
                <sql:param value="${param.vierstellZahl}" />
                <sql:param value="${param.GebDat}" />
                <sql:param value="${param.Zimmernummer}" />
                <sql:param value="${param.Buchungsnummer}" />
                <sql:param value="${param.Buchungsdatum}" />
                <sql:param value="${param.Beginnzeit}" />
                <sql:param value="${param.Endzeit}" />
            </sql:update>





        </c:catch>

        <c:if test="${exception!=null}">
            <c:out value="Unable to insert data in database. PLEASE BE SURE TO USE A UNIQUE CODE that is not used yet" />
        </c:if>
    </c:if>
</div>

</body>
</html>