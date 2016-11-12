<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Test</title>
</head>
<body>
    <c:if test="${ sessionScope.type == admin }">
        <%@ include file="menu_admin.jsp" %>
    </c:if>
    <c:if test="${ sessionScope.type == stagiaire }">
        <%@ include file="menu_stagiaire.jsp" %>
    </c:if>
    
    <c:if test="${ !empty User }">
    	<p style="color:red;"><c:out value="Connecté en tant que ${ User.email }  (${User.typeUtilisateur})" /></p>
    </c:if>
    
</body>
</html>