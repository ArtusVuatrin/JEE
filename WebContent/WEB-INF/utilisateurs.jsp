<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Utilisateurs</title>
</head>
<body>
    <p style="color:red;"><c:out value="Connecté en tant que ${ sessionScope.email }" /></p>
    <%@ include file="menu_admin.jsp" %>
    
    
    <c:if test="${ !empty information }">
    	<p style="color:red;"><c:out value="${ information }" /></p>
   	</c:if>
    
	<h1>Liste des Utilisateurs</h1>

   	<table>
   		<tr>
   			<td>Email</td>
   			<td>Nom</td>
   			<td>Type d'Utilisateur</td>
   			<td>Activité</td>
   			<td></td>
   		</tr>
		<c:forEach items="${ Users }" var="User">
	    		<tr class="form">
	    			<td>
	    				<input type="hidden" value="${ User.getId() }" name="id" />
	    				<input type="hidden" value="${ User.getTypeUtilisateur() }" name="type" />
	    				<input type="text" value="<c:out value="${ User.getEmail() }" />" name="email" />
	    			</td>
	    			<td><input type="text" value="<c:out value="${ User.getNom() }" />" name="name" /></td>
	    			<td><c:out value="${ User.getTypeUtilisateur() }" /></td>
	    			<td>
	    				<input type="radio" name="activity${ User.getId() }${ User.getTypeUtilisateur() }" value="1"<c:out value="${ User.getActif() ? 'checked' : '' }" /> />Actif<br />
	    				<input type="radio" name="activity${ User.getId() }${ User.getTypeUtilisateur() }" value="0" <c:out value="${ User.getActif() ? '' : 'checked' }" /> />Inactif
	    			
	    			</td>
	    			<td>
					  	<input type="submit" value="Modifier" name="modify" onClick="submitForm(this)" />
					  	<form class="form" action="/JEE/Utilisateurs" method="post">
	    					<input type="hidden" value="<c:out value="${ User.getId() }" />" name="id" />
	    					<input type="hidden" value="<c:out value="${ User.getTypeUtilisateur() }" />" name="type" />
					  		<input type="submit" value="Supprimer" name="delete" onClick="submitForm(this)" />
				  		</form>
	    			</td>
	    		<tr>
		</c:forEach>
   	</table>
   	
	<h1>Ajouter un utilisateur</h1>
	
	<form method="post" action="/JEE/Utilisateurs">
	    <table>
	   		<tr>
	   			<td>Email</td>
	   			<td><input type="text" name="email" /></td>
	   		</tr>
	   		<tr>
	   			<td>Nom</td>
	   			<td><input type="text" name="name" /></td>
	   		</tr>
	   		<tr>
	   			<td>Type d'Utilisateur</td>
	   			<td>
	   				<input type="radio" name="type" value="administrateur" />Admin<br>
	   				<input type="radio" name="type" value="stagiaire" />Stagiaire
	   			</td>
	   		<tr>
	   			<td>Société</td>
	   			<td><input type="text" name="societe" /></td>
	   		</tr>
	   		<tr>
	   			<td>Mot de Passe</td>
	   			<td><input type="text" name="password" /></td>
	   		</tr>
	   		<tr>
	   			<td>Telephone</td>
	   			<td><input type="text" name="tel" /></td>
	   		</tr>
	   		<tr>
	   			<td></td>
	   			<td><input type="submit" value="Ajouter" name="addUser" /></td>
	   		</tr>
   		</table>
	</form>
	
   	<form method="post" action="/JEE/Utilisateurs" id="poster">
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