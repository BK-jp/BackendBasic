export default class CustomPopup {
	constructor(){}
	
	init(classArray){
		if(document.head.querySelector('link[href="/resources/modules/CustomPopup/style.css"') == null){
			let style = document.createElement('link');
			style.rel = 'stylesheet';
			style.type = 'text/css';
			style.href = '/resources/modules/CustomPopup/style.css';
			
			document.head.appendChild(style);
		}
		
		let CustomPopup = document.getElementById('CustomPopup');
		
		if(CustomPopup == null){
			CustomPopup = document.createElement('div');
			CustomPopup.id = 'CustomPopup';
			
			document.body.appendChild(CustomPopup);
			
			const inner = document.createElement('div');
			inner.classList.add('popup-inner');
			
			const messageBox = document.createElement('div');
			messageBox.classList.add('popup-message-box');
			
			const textBox = document.createElement('div');
			textBox.classList.add('popup-text-box');
			
			const buttonWrap = document.createElement('div');
			buttonWrap.classList.add('popup-button-wrap');
			
			inner.appendChild(messageBox);
			inner.appendChild(textBox);
			inner.appendChild(buttonWrap);
			CustomPopup.appendChild(inner);
			
			const resizeEvent = function(){
				if(CustomPopup.clientWidth >= inner.clientWidth){
					CustomPopup.style.justifyContent = 'center';
				}else{
					CustomPopup.style.justifyContent = 'flex-start';
				}
				
				if(CustomPopup.clientHeight >= inner.clientHeight){
					CustomPopup.style.alignItems = 'center';
				}else{
					CustomPopup.style.justifyContent = 'flex-start';
				}
			}
			
			window.removeEventListener('resize', resizeEvent);
			window.addEventListener('resize', resizeEvent);
			
			const keyDownEvent = function(e){
				if(e.keyCode == 27 || e.which == 27){
					buttonWrap.querySelector('button.cancel').click();
				}
			}
			
			document.removeEventListener('keydown', keyDownEvent);
			document.addEventListener('keydown', keyDownEvent);
		}
		
		if(classArray != undefined) CustomPopup.classList.add(classArray);
	}
	
	appendText(placeholder){
		const popup = document.getElementById('CustomPopup');
		const input = document.createElement('input');
		
		if(placeholder == undefined) placeholder = '';
		
		input.type = 'text';
		input.placeholder = placeholder;
		
		const textBox = popup.querySelector('.popup-text-box');
		
		textBox.innerHTML = '';
		textBox.appendChild(input);
	}
	
	appendButtons(){
		const popup = document.getElementById('CustomPopup');
		const buttonWrap = popup.querySelector('.popup-button-wrap');
		buttonWrap.innerHTML = '';
		
		const buttons = [document.createElement('button'), document.createElement('button')];
		buttons[0].classList.add('cancel');
		buttons[0].innerText = '취소';
		
		buttons[1].classList.add('confirm');
		buttons[1].innerText = '확인';
		
		buttons.forEach((button)=>{
			buttonWrap.appendChild(button);
		});
	}
	
	static alert(message, callback, options){
		CustomPopup.prototype.init(options != undefined ? options.type : options);
		CustomPopup.prototype.appendButtons();
		
		const popup = document.getElementById('CustomPopup');
		popup.querySelector('.popup-message-box').innerHTML = message;
		
		popup.querySelector('button.confirm').addEventListener('click', function(){
			popup.classList.remove('alert', 'confirm', 'prompt');
			popup.classList.remove('show');
			
			if(callback != undefined) callback();
		});
		
		popup.querySelector('button.cancel').addEventListener('click', function(){
			popup.classList.remove('alert', 'confirm', 'prompt');
			popup.classList.remove('show');
			
			if(callback != undefined) callback();
		});
		
		popup.classList.add('alert', 'show');
		popup.querySelector('button.confirm').focus();
		window.dispatchEvent(new Event('resize'));
	}
	
	static confirm(message, callback, options){
		CustomPopup.prototype.init(options != undefined ? options.type : options);
		CustomPopup.prototype.appendButtons();
		
		const popup = document.getElementById('CustomPopup');
		popup.querySelector('.popup-message-box').innerHTML = message;
		
		popup.querySelector('button.confirm').addEventListener('click', function(){
			popup.classList.remove('alert', 'confirm', 'prompt');
			popup.classList.remove('show');
			callback(true);
		});
		
		popup.querySelector('button.cancel').addEventListener('click', function(){
			popup.classList.remove('alert', 'confirm', 'prompt');
			popup.classList.remove('show');
			callback(false);
		});
		
		popup.classList.add('confirm', 'show');
		popup.querySelector('button.cancel').focus();
		window.dispatchEvent(new Event('resize'));
	}
	
	static prompt(message, callback, options){
		CustomPopup.prototype.init(options != undefined ? options.type : options);
		CustomPopup.prototype.appendButtons();
		CustomPopup.prototype.appendText(options != undefined ? options.placeholder : options);
		
		const popup = document.getElementById('CustomPopup');
		popup.querySelector('.popup-message-box').innerHTML = message;
		
		popup.querySelector('button.confirm').addEventListener('click', function(){
			popup.classList.remove('alert', 'confirm', 'prompt');
			popup.classList.remove('show');
			callback(popup.querySelector('.popup-text-box').querySelector('input').value);
		});
		
		popup.querySelector('button.cancel').addEventListener('click', function(){
			popup.classList.remove('alert', 'confirm', 'prompt');
			popup.classList.remove('show');
			callback('');
		});
		
		popup.classList.add('prompt', 'show');
		popup.querySelector('button.cancel').focus();
		window.dispatchEvent(new Event('resize'));
	}
}