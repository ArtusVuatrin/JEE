<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Test</title>
</head>
<body>
    <c:if test="${ !empty erreur }"><p style="color:red;"><c:out value="${ erreur }" /></p></c:if>
    <form method="post" action="">
        <p>
            <label for="email">Email : </label>
            <input type="text" name="email" id="email" />
        </p>
        <p>
            <label for="password">Mot de Passe : </label>
            <input type="password" name="password" id="password" />
        </p>
        
        <input type="submit" />
    </form>
     
</body>
</html>