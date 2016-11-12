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