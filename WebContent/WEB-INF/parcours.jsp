<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8" />
        <title>Questionnaire</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
        <div>
            <form method="post" action="Parcours">

                    <c:forEach items="${ questions }" var="question" varStatus="status">

                        <h2>Question ${ status.count }</h2> 
                        <p><b>${ question.intitule }</b></p>

                        <c:forEach items="${ reponses[status.index] }" var="reponse">
                            <INPUT type= "radio" name="${ question.id }" value="${ reponse.id }"> ${ reponse.intitule } <br/>
                        </c:forEach>

                    </c:forEach>
    
                <input type="submit" value="Valider"  />
                <input type="reset" value="Réinitialiser" /> <br />

            </form>
        </div>
    </body>
</html>