const Ajax = {
	send: function(dataObj){
		document.getElementById('loading').classList.add('show');
		
		let xhr = new XMLHttpRequest();
		let parameter = null;
		
		if(dataObj.type == 'GET'){
			if(dataObj.data != undefined){
				parameter = Ajax.createParameter(dataObj.data);
			}
			
			xhr.open(dataObj.type, encodeURI(dataObj.url+(parameter == null ? '' : '?'+parameter)), true);
		}else{
			xhr.open(dataObj.type, dataObj.url, true);
		}
		
		xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
		
		if(dataObj.encType != undefined){
			xhr.setRequestHeader('Content-Type', dataObj.encType);
		}
		
		xhr.onload = async ()=>{
			if(xhr.readyState === XMLHttpRequest.DONE){
				const status = xhr.status;
				
				if(status === 0 || (status >= 200 && status < 400)){
					if(xhr.getResponseHeader('Content-Type').indexOf('application/json') != -1){
						try{
							const responseJson = JSON.parse(xhr.response);
							dataObj.success(responseJson, xhr);
						}catch(e){
							console.log(e);
							if(xhr.responseType == 'blob'){
								const responseData = JSON.parse(await xhr.response.text());
								dataObj.success(responseData, xhr);
							}else{
								dataObj.success(xhr.response, xhr);
							}
						}
					}else{
						dataObj.success(xhr.response, xhr);
					}
					
					if(dataObj.complete != undefined){
						dataObj.complete();
					}
				}else{
					console.log('error');
				}
				
				if(dataObj.finally != undefined){
					dataObj.finally();
				}
				
				document.getElementById('loading').classList.remove('show');
			}
		};
		
		if(dataObj.data != undefined){
			if(dataObj.encType == undefined && dataObj.type.toUpperCase() == 'POST'){
				xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8;');
			}
			
			if(dataObj.data instanceof FormData){
				parameter = dataObj.data;
			}else{
				parameter = Ajax.createParameter(dataObj.data);
			}
		}
		
		if(dataObj.beforeSend != undefined){
			dataObj.beforeSend(xhr);
		}
		
		if(parameter != null && dataObj.type != 'GET'){
			xhr.send(parameter);
		}else{
			xhr.send();
		}
	},
	createParameter: function(obj){
		let parameter = '';
		
		for(let key in obj){
			if(parameter.length != 0) parameter += '&';
			if(obj[key] instanceof Object){
				parameter += Ajax.parseObjectToMapString(key, obj[key]);
			}else{
				parameter += key;
				parameter += '=';
				parameter += obj[key]
			}
		}
		
		return parameter;
	},
	parseObjectToMapString: function(objectName, obj){
		let result = '';
		
		for(let key in obj){
			if(result.length != 0) result += '&';
			if(obj[key] instanceof Object){
				result += objectName+'['+key+']='+Ajax.parseObjectToMap(key, obj[key]);
			}else{
				result += objectName+'['+key+']='+obj[key];
			}
		}
		
		return result;
	}
};

export default Ajax;