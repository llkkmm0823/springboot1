function idCheck(){
	if(document.frm.userid.value==""){
		alert('아이디를 입력하여 주심시오');
		document.frm.userid.focus();
		return;
	}
	var k = document.frm.userid.value
	var opt = "toolbar=no, menubar=no,resizable=no,width=450,height=200";
	window.open("idCheck?userid="+k, "id Check", opt);        
}


function idok(id){
        opener.frm.userid.value = id;
        opener.frm.re_id.value = id;
        self.close();
}

function boardCheck(){
	if(document.frm.title.value==''){
		alert("제목을입력하세요");
		document.frm.title.focus();
		return false;
	}else if(document.frm.content.value==''){
		alert("내용을입력하세요");
		document.frm.content.focus();
		return false;
	}else{
		return true;
	}
}