<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<title>Espace personnel</title>
</head>

<body>
    <c:if test="${ !empty erreur }"><p style="color:red;"><c:out value="${ erreur }" /></p></c:if>

    <h1>Questionnaires</h1>

    <c:choose>
        <c:when test="${ empty questionnaires }">
            Aucun questionnaire disponible.
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach var="questionnaire" items="${ questionnaires }">
                    <li><a href="/JEE/Parcours?questionnaire=${ questionnaire.id }"> ${ questionnaire.sujet } </a></li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose> 
    
    
    <h1>Questionnaires effectués</h1>
    
    <c:choose>
        <c:when test="${ empty parcourss }">
            Vous n'avez rempli aucun questionnaire.
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Questionnaire</th>
                    <th>Score</th>
                    <th>Duree</th>
                </tr>
                <c:forEach var="parcours" items="${ parcourss }">
                    <tr>
                        <td> ${ parcours.sujetQuestionnaire } </td>
                        <td> ${ parcours.score } / ${ parcours.nbQuestions } </td>
                        <td> ${ parcours.duree / 60} min </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
     
</body>

</html>