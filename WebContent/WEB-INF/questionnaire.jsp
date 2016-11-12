<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questionnaire <c:out value="${ Questionnaire.getSujet() }" /></title>
</head>
<body>
    <p style="color:red;"><c:out value="Connecté en tant que ${ sessionScope.email }" /></p>
    <%@ include file="menu_admin.jsp" %>
    
    
    <c:if test="${ !empty information }">
    	<p style="color:red;"><c:out value="${ information }" /></p>
   	</c:if>
    
    <h1>Test : <c:out value="${ Questionnaire.getSujet() }" /></h1>
	<h3>Liste des Questions</h3>

   	<table>
   		<tr>
   			<td>Question</td>
   			<td>Reponses</td>
   		</tr>
		<c:forEach items="${ Questions }" var="Question" varStatus="statut">
	    		<tr class="form">
	    			<td >
	    				<div class="form">
		    				<input type="hidden" value="${ Question.getId() }" name="idQuestion">
	    			    	<c:out value="${ statut.count }-"/><input type="text" value="${ Question.getIntitule() }" name="intituleQuestion"/><br />
		    				<input type="submit" value="modifier" name="modifyQuestion"  onClick="submitForm(this)">
	    				</div>
	    				<div class="form">
		    				<input type="hidden" value="${ Question.getId() }" name="idQuestion">
		    				<input type="submit" value="supprimer" name="deleteQuestion"  onClick="submitForm(this)">
	    				</div>
	    			</td>
	    			<td>
		    			<table class="form">
							<c:forEach items="${ Question.getReponses() }" var="Reponse"  varStatus="status">
		    					<tr class="form">
		    						<td>
		    							<input type="hidden" value="${ Question.getId() }" name="idQuestion">
		    							<input type="radio" value="${ Reponse.getId() }" name="actif${ Question.getId() }" <c:out value="${ Reponse.getValide() ? 'checked' : ''}" /> />
	    							</td>
	    							<td>
	    								<div class="form">
						    				<input type="hidden" value="${ Reponse.getId() }" name="idReponse">
		    								<c:out value="${ status.count })"/><input type="text" value="${ Reponse.getIntitule() }" name="intituleReponse" /><br />
						    				<input type="submit" value="modifier" name="modifyReponse"  onClick="submitForm(this)" >
					    				</div>
					    				<form method="post" class="form" action="/JEE/Questionnaire">
						    				<input type="hidden" value="${ Reponse.getId() }" name="idReponse">
						    				<input type="submit" value="supprimer" name="deleteReponse"  onClick="submitForm(this)" >
					    				</form>
	    							</td>
	    						</tr>
							</c:forEach>
							<tr>
								<td>
									<input type="submit" value="Modifier la bonne réponse" name="modifyOrder" onClick="submitForm(this)">
								</td>
								<td class="form">
									<input type="hidden" name="question" value="${ Question.getId() }">
									<input type="hidden" name="position" value="${ Question.getReponses().size() + 1  }">
									<input type="hidden" name="valide" value="${ Question.getReponses().size() >= 1 ? '0' : '1' }">
									<input type="text" name="newReponse" placeholder="Nouvelle réponse"/>
									<input type="submit" value="Ajouter" name="addReponse" onClick="submitForm(this)"/>
								</td>
							</tr>
						</table>
	    			</td>
	    		<tr>
		</c:forEach>
		<tr class="form">
			<td>
				<input type="hidden" name="position" value="${ Questions.size() + 1 }">
				<input type="text" name="newQuestion" placeholder="Nouvelle question" />
			</td>
			<td>
				<input type="submit" name="addQuestion" value="Ajouter" onClick="submitForm(this)" />
			</td>
		</tr>
   	</table>
	<form method="post" action="/JEE/Questionnaire?id=${ Questionnaire.getId() }" id="poster">
   	</form>

</body>
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
</html>