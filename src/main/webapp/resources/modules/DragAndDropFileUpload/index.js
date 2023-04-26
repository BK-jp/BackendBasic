export default class DragAndDropFileUpload{
	constructor(id){
		this.element = document.getElementById(id);
		this.init();
		
		this.files = new Array();
	}
	
	init(){
		const element = this.element;
		
		if(document.head.querySelector('link[href="/resources/modules/DragAndDropFileUpload/style.css"]') == null){
			const link = document.createElement('link');
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = '/resources/modules/DragAndDropFileUpload/style.css';
			
			document.head.appendChild(link);
		}
		
		element.classList.add('FileUpload');
		
		const fileList = document.createElement('ul');
		element.appendChild(fileList);
		
		const dropArea = document.createElement('div');
		dropArea.classList.add('DropArea');
		element.appendChild(dropArea);
		
		dropArea.innerHTML = '해당 영역을 클릭 혹은 파일을 드래그해주세요.';
		
		const fileInput = document.createElement('input');
		fileInput.type = 'file';
		fileInput.multiple = true;
		
		dropArea.appendChild(fileInput);
		
		const thisClass = this;
		
		dropArea.addEventListener('click', function(){
			fileInput.click();
		});
		
		const dragEvent = function(e){
			e.stopPropagation();
			e.preventDefault();
			
			if(e.type == 'dragenter' || e.type == 'mouseenter'){
				dropArea.style.border = '1px solid #06ABC6';
				dropArea.style.cursor = 'pointer';
			}else{
				dropArea.style.border = '1px solid #F1F1F1';
				dropArea.style.cursor = 'auto';
			}
		};
		
		const dropEvent = function(e){
			e.stopPropagation();
			e.preventDefault();
			
			const eventFiles = e.target.files || e.dataTransfer.files;
			const files = thisClass.files;
			
			if(eventFiles.length > 0){
				for(let i=0;i<eventFiles.length;i++){
					files.push(eventFiles[i]);
				}
			}
			
			thisClass.appendFiles();
		};
		
		dropArea.addEventListener('mouseenter', dragEvent);
		dropArea.addEventListener('mouseout', dragEvent);
		dropArea.addEventListener('dragenter', dragEvent);
		dropArea.addEventListener('dragout', dragEvent)
		dropArea.addEventListener('drop', dropEvent);
		
		const fileInputChangeEvent = function(e){
			const eventFiles = e.target.files;
			const files = thisClass.files;
			
			if(eventFiles.length > 0){
				for(let i=0;i<eventFiles.length;i++){
					files.push(eventFiles[i]);
				}
			}
			
			this.value = '';
			thisClass.appendFiles();
		};
		
		fileInput.addEventListener('change', fileInputChangeEvent);
	}
	
	appendFiles(){
		const element = this.element;
		const files = this.files;
		const dropArea = element.querySelector('.DropArea');
		const fileList = element.querySelector('ul');
		
		const thisClass = this;
		
		fileList.innerHTML = '';
		if(files.length > 0){
			fileList.style.padding = '16px';
			for(let i=0;i<files.length;i++){
				const li = document.createElement('li');
				
				if(files[i] instanceof File){
					li.innerHTML = 
						'<div class="FileName">'+files[i].name+'</div>'
						+'<div class="FileDate">'+this.parseDate(new Date()).substring(0,16)+'</div>'
						+'<div class="FileDeleteWrap"><button type="button" class="FileDelete" data-idx="'+i+'"><span></span><span></span></button></div>'
					;
				}else{
					li.innerHTML = 
						'<div class="FileName">'+files[i].name+'</div>'
						+'<div class="FileDate">'+files[i].reg_date.substring(0,16)+'</div>'
						+'<div class="FileDeleteWrap"><button type="button" class="FileDelete" data-idx="'+i+'"><span></span><span></span></button></div>'
					;
				}
				
				li.querySelector('.FileDelete').addEventListener('click', function(){
					const idx = this.dataset.idx;
					files.splice(idx, 1);
					thisClass.appendFiles();
				});
				
				fileList.appendChild(li);
				dropArea.innerHTML = '추가 업로드';
			}
		}else{
			fileList.style.padding = '0';
			dropArea.innerHTML = '해당 영역을 클릭 혹은 파일을 드래그해주세요.';
		}
	}
	
	parseDate(date){
		return date.getFullYear()+'-'+this.parseNumber(date.getMonth()+1)+'-'+this.parseNumber(date.getDate())+' '+this.parseNumber(date.getHours())+':'+this.parseNumber(date.getMinutes())+':'+this.parseNumber(date.getSeconds());
	}
	
	parseNumber(number){
		if(number > 9){
			return number.toString();
		}else{
			return '0'+number;
		}
	}
	
	get element() {return this._element;}
	set element(element) {this._element = element;}
	
	get files(){return this._files;}
	set files(files){this._files = files; this.appendFiles();}
}