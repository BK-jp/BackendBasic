const InputChecker = {
	inputControl: {
		number: function(element){
			if(element.value.replace(/[0-9|-]/g, '').length > 0){
				element.value = element.value.replace(/[^0-9|^-]/g, '');
			}
			
			if(element.value.length > 0){
				if(element.value.substring(1, 2) == '-'){
					element.value = element.value.substring(0, 1);
				}
			}
			
			if(element.value != '' && element.value != '-')	element.value = parseInt(element.value);
		},
		unsignedNumber: function(element){
			if(element.value.replace(/[0-9]/g, '').length > 0){
				element.value = element.value.replace(/[^0-9]/g, '');
			}
			if(element.value.length > 0){
				element.value = parseInt(element.value);
			}else{
				element.value = 0;
			}
		},
		phone: function(element){
			let value = element.value.replace(/[-]/g, '');
			
			if(value.replace(/[0-9]/g, '').length > 0){
				element.value = element.value.replace(/[^0-9&^-]/g, '');
				return false;
			}
			
			if(value.length <= 2){
				element.value = value;
			}else if(value.length <= 3){
				if(value.substring(0,2) == '02'){
					element.value = value.substring(0,2)+'-'+value.substring(2, value.length);
				}else{
					element.value = value;
				}
			}else if(value.length <= 5){
				if(value.substring(0,2) == '02'){
					element.value = value.substring(0,2)+'-'+value.substring(2, value.length);
				}else{
					element.value = value.substring(0,3)+'-'+value.substring(3, value.length);
				}
			}else if(value.length <= 6){
				if(value.substring(0,2) == '02'){
					element.value = value.substring(0,2)+'-'+value.substring(2,5)+'-'+value.substring(5,value.length);
				}else{
					element.value = value.substring(0,3)+'-'+value.substring(3, value.length);
				}
			}else if(value.length <= 9){
				if(value.substring(0,2) == '02'){
					element.value = value.substring(0,2)+'-'+value.substring(2,5)+'-'+value.substring(5,value.length);
				}else{
					element.value = value.substring(0,3)+'-'+value.substring(3,6)+'-'+value.substring(6, value.length);
				}
			}else if(value.length <= 10){
				element.value = value.substring(0,3)+'-'+value.substring(3,6)+'-'+value.substring(6, value.length);
			}else if(value.length == 11){
				element.value = value.substring(0,3)+'-'+value.substring(3,7)+'-'+value.substring(7, value.length);
			}else{
				element.value = element.value.substring(0, element.value.length-1);
			}
		},
		businessNumber: function(element){
			let value = element.value.replace(/[-]/g, '');
			
			if(value.replace(/[0-9]/g, '').length > 0){
				element.value = element.value.substring(0, element.value.length-1);
				return false;
			}
			
			if(value.length <= 3){
				element.value = value;
			}else if(value.length <= 5){
				element.value = value.substring(0,3)+'-'+value.substring(3, value.length);
			}else if(value.length <= 10){
				element.value = value.substring(0,3)+'-'+value.substring(3,5)+'-'+value.substring(5,value.length);
			}else{
				element.value = element.value.substring(0, element.value.length-1);
			}
		}
	},
	check: {
		emptyCheck: function(element){
			let returnObj = {
				result: false
			};
			
			if(element.value.replace(/[\s]/g,'') == ''){
				returnObj.result = false;
			}else{
				returnObj.result = true;
			}
			
			return returnObj;
		},
		phoneCheck: function(element){
			let returnObj = {
				result: false,
				message: ''
			};
			
			InputChecker.inputControl.phone(element);
			
			if(element.value.substring(0,2) == '02'){
				if(element.value.length != 11){
					returnObj.result = false;
					returnObj.message = '연락처를 정확히 입력해주세요.';
				}else{
					returnObj.result = true;
					returnObj.message = '';
				}
			}else if(element.value.substring(0,3) == '010' || element.value.substring(0,3) == '070'){
				if(element.value.length != 13){
					returnObj.result = false;
					returnObj.message = '연락처를 정확히 입력해주세요.';
				}else{
					returnObj.result = true;
					returnObj.message = '';
				}
			}else{
				if(element.value.length != 12){
					returnObj.result = false;
					returnObj.message = '연락처를 정확히 입력해주세요.';
				}else{
					returnObj.result = true;
					returnObj.message = '';
				}
			}
			
			return returnObj;
		},
		emailCheck: function(element){
			let returnObj = {
				result: false,
				message: ''
			};
			
			const regEmail = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
			if(!regEmail.test(element.value)){
				returnObj.result = false;
				returnObj.message = '이메일을 정확히 입력해주세요.';
			}else{
				returnObj.result = true;
				returnObj.message = '';
			}
			
			return returnObj;
		},
		confirmNumberCheck: function(element){
			let returnObj = {
				result: false,
				message: ''
			};
			
			if(element.value.length < 6 || element.value.length > 6){
				returnObj.result = false;
				returnObj.message = '인증번호를 정확히 입력해주세요.';
			}else{
				returnObj.result = true;
				returnObj.message = '';
			}
			
			return returnObj;
		},
		phoneConfirmCheck: function(confirmNumberElement, confirmNumber){
			let returnObj = {
				result: false,
				message: ''
			};
			
			if(confirmNumber == undefined || confirmNumber == null || confirmNumber == ''){
				returnObj.result = false;
				returnObj.message = '인증하기 버튼을 눌러주세요.';
			}else if(confirmNumberElement.value != confirmNumber){
				returnObj.result = false;
				returnObj.message = '인증번호가 일치하지 않습니다.';
			}else{
				returnObj.result = true;
				returnObj.message = '인증되었습니다.';
			}
			
			return returnObj;
		},
		checkOneCheck: function(elements){
			let returnObj = {
				result: false
			};
			
			for(let i=0;i<elements.length;i++){
				if(elements[i].checked){
					returnObj.result = true;
					return returnObj;
				}
			}
			
			return returnObj;
		},
		checkAllCheck: function(elements){
			let returnObj = {
				result: true
			};
			
			for(let i=0;i<elements.length;i++){
				if(!elements[i].checked){
					returnObj.result = false;
					return returnObj;
				}
			}
			
			return returnObj;
		},
		passwordCheck: function(element){
			let returnObj = {
				result: false,
				message: ''
			};
			
			let emptyCheck = InputChecker.check.emptyCheck(element);
			
			if(!emptyCheck.result){
				returnObj.result = false;
				returnObj.message = '비밀번호를 입력해주세요.';
			}else if(element.value.replace(/[a-z|A-Z|0-9|`~!@#$%^&*\|\\\'\";:\/?.,<>\[\}+=\-_]/gi, '').length > 0){
				returnObj.result = false;
				returnObj.message = '영대소문자, 숫자, 특수문자만 입력 가능합니다.';
			}else if(element.value.replace(/[`~!@#$%^&*\|\\\'\";:\/?.,<>\[\}+=\-_]/gi, '').length == element.value.length){
				returnObj.result = false;
				returnObj.message = '비밀번호에 특수문자를 포함시켜주세요.';
			}else{
				returnObj.result = true;
				returnObj.message = '';
			}
			
			return returnObj;
		},
		passwordConfirmCheck: function(confirmElement, passwordElement){
			let returnObj = {
				result: false,
				message: ''
			};
			
			let passwordCheck = InputChecker.check.passwordCheck(passwordElement);
			
			if(!passwordCheck.result){
				returnObj.result = false;
				returnObj.message = '비밀번호를 확인해주세요.';
			}else if(confirmElement.value != passwordElement.value){
				returnObj.result = false;
				returnObj.message = '비밀번호가 일치하지 않습니다.';
			}else{
				returnObj.result = true;
				returnObj.message = '';
			}
			
			return returnObj;
		},
		idCheck: function(element){
			let returnObj = {
				result: false,
				message: ''
			};
			
			if(element.value.replace(/[a-z|A-Z|0-9]/g, '').length > 0){
				returnObj.result = false;
				returnObj.message = '아이디는 영대소문자, 숫자로만 입력해주세요.';
			}else if(element.value.length < 5 || element.value.length > 20){
				returnObj.result = false;
				returnObj.message = '아이디는 5자 이상 20자 이하로 입력해주세요.';
			}else{
				returnObj.result = true;
				returnObj.message = '';
			}
			
			return returnObj;
		},
		businessNumberCheck: function(element){
			let returnObj = {
				result: false,
				message: ''
			};
			
			InputChecker.inputControl.businessNumber(element);
			
			if(element.value.length < 12){
				returnObj.result = false;
				returnObj.message = '사업자 번호를 정확히 입력해주세요.';
			}else{
				returnObj.result = true;
				returnObj.message = '';
			}
			
			return returnObj;
		}
	},
	sendConformNumber: function(phoneElement, callback){
		$.ajax({
			url: "/request/popbill/sendConfirmNumber",
			type: "POST",
			data:{
				phone: phoneElement.value
			},
			success: function(data){
				let result = data.result;
				
				callback(result);
			}
		});
	},
	sendUserConfirmNumber: function(phoneElement, callback){
		$.ajax({
			url: "/request/popbill/sendUserConfirmNumber",
			type: "POST",
			data:{
				phone: phoneElement.value
			},
			success: function(data){
				let result = data.result;
				
				callback(result);
			}
		});
	}
};

export default InputChecker;