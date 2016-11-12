<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questionnaires</title>
</head>
<body>
    <p style="color:red;"><c:out value="Connecté en tant que ${ sessionScope.email }" /></p>
    <%@ include file="menu_admin.jsp" %>
    
    
    <c:if test="${ !empty information }">
    	<p style="color:red;"><c:out value="${ information }" /></p>
   	</c:if>
    
	<h1>Liste des Questionnaires</h1>
	   	<table>
   		<tr>
   			<td>Sujet</td>
   			<td>Activité</td>
   			<td></td>
   		</tr>
		<c:forEach items="${ Questionnaires }" var="Questionnaire">
	    		<tr class="form">
	    			<td>
	    				<input type="hidden" value="<c:out value="${ Questionnaire.getId() }" />" name="id" />
	    				<input type="text" value="<c:out value="${ Questionnaire.getSujet() }" />" name="sujet" />
	    			</td>
	    			<td>
	    				<input type="radio" name="activity${ Questionnaire.getId() }" value="1"<c:out value="${ Questionnaire.getActif() ? 'checked' : '' }" /> />Actif<br />
	    				<input type="radio" name="activity${ Questionnaire.getId() }" value="0"<c:out value="${ Questionnaire.getActif() ? '' : 'checked' }" /> />Inactif
	    			</td>
	    			<td>
					  	<input type="submit" value="Modifier" name="modify" onClick="submitForm(this)" />
					  	<a href="/JEE/Questionnaire?id=<c:out value="${ Questionnaire.getId() }" />">Voir</a>
					  	<form class="form" action="/JEE/Questionnaires" method="post">
	    					<input type="hidden" value="<c:out value="${ Questionnaire.getId() }" />" name="id" />
					  		<input type="submit" value="Supprimer" name="delete" onClick="submitForm(this)" />
				  		</form>
	    			</td>
	    		<tr>
		</c:forEach>
   	</table>
   	
   	<h1>Ajouter un Questionnaire</h1>
	
	<form method="post" action="/JEE/Questionnaires">
	    <table>
	   		<tr>
	   			<td>Sujet</td>
	   			<td><input type="text" name="sujet" /></td>
	   		</tr>
	   		<tr>
	   			<td></td>
	   			<td><input type="submit" value="Ajouter" name="addQuestionary" /></td>
	   		</tr>
   		</table>
	</form>
	
   	<form method="post" action="/JEE/Questionnaires" id="poster">
   	</form>

<script>
function submitForm(element){
	elm = element;
	while(element.className != "form")
		element = element.parentNode;

	var form = document.getElementById("poster");
	var inputs = element.getElementsByTagName("input");
	while(inputs.length > 0)
		form.appendChild(inputs[0]);
	
	form.appendChild(elm);
	return;
}
</script>

</body>
</html>