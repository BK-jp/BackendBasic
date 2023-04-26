import Ajax from '../Ajax.js';
import CustomPopup from '../CustomPopup/index.js';

const Login = {
	init: function(){
		Login.registrateEvent();
	},
	registrateEvent: function(){
		const loginForm = document.getElementById('login-form');
		const loginFormEvent = function(e){
			e.preventDefault();
			
			Ajax.send({
				url: '/auth/login',
				type: 'POST',
				data: {
					email: document.getElementById('request-email').value,
					password: document.getElementById('request-password').value
				},
				success: function(data){
					if(data.code == 0){
						window.location.href = data.targetUrl;
					}else{
						CustomPopup.alert(data.message, ()=>{
							if(data.targetUrl != ''){
								window.location.href = data.targetUrl;
							}
						});
					}
				}
			});
		}
		
		loginForm.removeEventListener('submit', loginFormEvent);
		loginForm.addEventListener('submit', loginFormEvent);
	}
};

window.addEventListener('load', Login.init);