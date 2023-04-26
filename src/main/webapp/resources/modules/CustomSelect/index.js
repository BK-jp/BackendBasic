export default class CustomSelect{
	constructor(id, options){
		if(options.icon == undefined) options.icon = false;
		if(options.type == undefined) options.type = 'default';
		
		this.setId(id);
		this.setIcon(options.icon);
		this.setType(options.type);
		this.init();
	}
	
	init(){
		const element = document.getElementById(this.getId());
		const type = this.getType();
		
		element.innerHTML = '';
		
		if(document.head.querySelector('link[href="/resources/modules/CustomSelect/style.css"]') == null){
			let style = document.createElement('link');
			style.href = '/resources/modules/CustomSelect/style.css';
			style.type = 'text/css';
			style.rel = 'stylesheet';
			document.head.appendChild(style);
		}
		
		element.classList.add('CustomSelect');
		if(type != 'default') element.classList.add(type);
		
		const selected = document.createElement('div');
		selected.classList.add('selected');
		
		const arrow = document.createElement('div');
		arrow.classList.add('arrow');
		
		const optionWrap = document.createElement('div');
		optionWrap.classList.add('option-wrap');
		
		const optionList = document.createElement('ul');
		optionWrap.appendChild(optionList);
		
		const select = document.createElement('select');
		
		element.appendChild(selected);
		element.appendChild(arrow);
		element.appendChild(optionWrap);
		element.appendChild(select);
		
		if(this.isIcon()){
			element.classList.add('include-icon');
			
			const icon = document.createElement('div');
			icon.classList.add('icon');
			
			element.prepend(icon);
		}
		
		const thisClass = this;
		
		element.addEventListener('click', function(e){
			if(e.target.closest('.option-wrap') != optionWrap) this.classList.add('active');
		});
		
		select.addEventListener('change', function(){
			const value = this.value;
			
			element.querySelector('.selected').innerText = select.querySelector('option[value="'+value+'"]').innerText;
			
			if(thisClass.isIcon()){
				const dataset = select.querySelector('option[value="'+value+'"]').dataset;
				
				for(let key in dataset){
					switch(key){
					case 'image': element.querySelector('.icon').style.backgroundImage = 'url("'+dataset[key]+'")'; break;
					case 'border': element.querySelector('.icon').style.border = dataset[key]; break;
					case 'selected': element.querySelector('.icon').style.backgroundColor = dataset[key]; break;
					}
				}
			}
		});
		
		optionWrap.addEventListener('click', function(){
			element.classList.remove('active');
		});
	}
	
	appendOption(text, value, options){
		const element = document.getElementById(this.getId());
		const select = element.querySelector('select');
		
		const option = document.createElement('option');
		option.value = value;
		option.innerText = text;
		
		select.appendChild(option);
		
		const optionList = element.querySelector('.option-wrap').querySelector('ul');
		const li = document.createElement('li');
		
		li.innerHTML =
			'<label>'
				+'<input type="radio" name="'+element.getAttribute('id')+'" value="'+value+'">'
				+(this.isIcon() ? '<span class="icon"></span>' : '')
				+'<span class="option-text"></span>'
			+'</label>'
		
		const label = li.querySelector('label');
		
		label.querySelector('.option-text').innerText = text;
		
		if(options != undefined){
			for(let key in options){
				select.querySelector('option[value="'+value+'"]').dataset[key] = options[key];
			}
		}
		
		if(this.isIcon()){
			if(options == undefined) options = {};
			if(options.image == undefined) options.image = '/resources/modules/CustomSelect/question.png';
			if(options.border == undefined) options.border = '1px solid rgba(0,0,0,0)';
			if(options.selected == undefined) options.selected = 'rgba(0,0,0,0)';
			
			for(let key in options){
				select.querySelector('option[value="'+value+'"]').dataset[key] = options[key];
			}
			
			label.querySelector('.icon').style.backgroundImage = 'url("'+options.image+'")';
			label.querySelector('.icon').style.border = options.border;
		}
		
		optionList.appendChild(li);
		element.querySelector('.selected').innerText = select.querySelector('option:checked').innerText;
		
		if(this.isIcon()){
			const dataset = select.querySelector('option:checked').dataset;
			for(let key in dataset){
				switch(key){
				case 'image': element.querySelector('.icon').style.backgroundImage = 'url("'+dataset[key]+'")'; break;
				case 'border': element.querySelector('.icon').style.border = dataset[key]; break;
				case 'selected': element.querySelector('.icon').style.backgroundColor = dataset[key]; break;
				}
			}
		}
		
		const thisClass = this;
		
		label.querySelector('input').addEventListener('change', function(){
			if(select.value != this.value){
				select.value = this.value;
				select.dispatchEvent(new Event('change'));
				
				if(thisClass.isIcon()){
					const icons = this.closest('ul').querySelectorAll('.icon');
					
					icons.forEach((icon)=>{
						icon.style.backgroundColor = 'rgba(0,0,0,0)'
					});
					
					const selectedIcon = this.closest('label').querySelector('.icon');
					const selected = select.querySelector('option[value="'+this.value+'"]').dataset.selected;
					selectedIcon.style.backgroundColor = selected;
				}
			}
		});
	}
	
	empty(){
		const element = document.getElementById(this.getId());
		const select = element.querySelector('select');
		
		select.innerHTML = '';
		
		const optionList = element.querySelector('.option-wrap').querySelector('ul');
		optionList.innerHTML = '';
	}
	
	getValue(){
		return document.getElementById(this.getId()).querySelector('select').value;
	}
	
	setValue(value){
		const element = document.getElementById(this.getId());
		const select = element.querySelector('select');
		const radios = element.querySelector('.option-wrap').querySelectorAll('input');
		
		select.value = value;
		element.querySelector('.selected').innerText = select.querySelector('option[value="'+value+'"]').innerText;
		
		if(this.isIcon()){
			const dataset = select.querySelector('option[value="'+value+'"]').dataset;
			
			for(let key in dataset){
				switch(key){
				case 'image': element.querySelector('.icon').style.backgroundImage = 'url("'+dataset[key]+'")'; break;
				case 'border': element.querySelector('.icon').style.border = dataset[key]; break;
				case 'selected': element.querySelector('.icon').style.backgroundColor = dataset[key]; break;
				}
			}
		}
		
		radios.forEach(radio =>{
			if(radio.value == value){
				radio.checked = true;
				
				const icon = radio.closest('label').querySelector('.icon');
				icon.style.backgroundColor = 'rgba(0,0,0,0)'
			}else{
				radio.checked = false;
				
				const selectedIcon = radio.closest('label').querySelector('.icon');
				const selected = select.querySelector('option:checked').dataset.selected;
				selectedIcon.style.backgroundColor = selected;
			}
		});
	}
	
	getSelectedOption(){
		const element = document.getElementById(this.getId());
		const select = element.querySelector('select');
		
		return select.querySelector('option:checked');
	}
	
	addEventListener(event, func){
		document.getElementById(this.getId()).querySelector('select').addEventListener(event, func);
	}
	
	removeEventListener(event, func){
		document.getElementById(this.getId()).querySelector('select').removeEventListener(event, func);
	}
	
	dispatchEvent(event){
		document.getElementById(this.getId()).querySelector('select').dispatchEvent(event);
	}
	
	isIcon(){
		return this.icon;
	}
	
	setIcon(icon){
		this.icon = icon;
	}
	
	setId(id){
		this.id = id;
	}
	
	getId(){
		return this.id;
	}
	
	setType(type){
		this.type = type;
	}
	
	getType(){
		return this.type;
	}
}