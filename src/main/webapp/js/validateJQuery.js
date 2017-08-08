$(document).ready(function () {
	$("form[name=registerForm]").submit(function() {
		var fullName = $("input[name=fullName]");
		if (!validateFullName(fullName.val())) {			
			alert("Sorry, you entered an incorrect fullName!"+
			"\nPlease fix and try again."
			+"\nHint: 'FirstName LastName'");
			fullName.focus();
			highlight(fullName, true);
			return false;
		}	
		highlight(fullName, false);
		var phoneNumber = $("input[name=phoneNumber]");
		if(!validatePhoneNumber(phoneNumber.val())){
			alert("Sorry, you entered an incorrect phone number!"+
			"\nPlease fix and try again."
			+"\nHint: Use international format!");
			phoneNumber.focus();
			highlight(phoneNumber, true);
			return false;
		}
		highlight(phoneNumber, false);
		var email = $("input[name=email]");
		if (!validateEmail(email.val())) {
			alert("Sorry, you entered an incorrect email!\nPlease fix and try again.");
			email.focus();
			highlight(email, true);
			return false;
		}	
		highlight(email, false);
		var password = $("input[name=password]");
		if (!validatePassword(password.val())) {
			alert("Sorry, you entered an incorrect password!" +
			  "\nPlease fix and try again." +
			  "\n\nPassword requirements:"+
			  "\n\t* At least one upper case English letter"+
			  "\n\t* At least one lower case English letter"+
			  "\n\t* At least one digit"+
			  "\n\t* At least one special character"+
			  "\n\t* Minimum eight in length");
			password.focus();
			highlight(password, true);
			return false;
		}	
		highlight(password, false);		
		var passwordCheck = $("input[name=passwordCheck]");
		if(password.val() != passwordCheck.val()){
			alert("Sorry, you entered an incorrect repeated password!"+
			"\nPlease fix and try again.");
			passwordCheck.focus();
			highlight(passwordCheck, true);
			return false;
		}
		highlight(passwordCheck, false);
		return true;
	});
});
function validateEmail(email) {
    var regExEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regExEmail.test(email);
}

function validatePassword(password) {
    var regExPasw = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;  
    return regExPasw.test(password);
}

function validateFullName(fullName) {
    var regExFullName = /^([a-zA-Z]{2,}\s[a-zA-z]{1,}'?-?[a-zA-Z]{2,}\s?([a-zA-Z]{1,})?)$/;
    return regExFullName.test(fullName);
}

function validatePhoneNumber(phoneNumber) {
	var digits = phoneNumber.replace(/\D/g,'');
    return digits.length == 11 || digits.length == 12;
}

function highlight(element, flag) {
	if (flag){
		element.css({ 'border': '#FF0000 1px solid'});
	} 
	else {
		element.css('border','transparent');
	}
}