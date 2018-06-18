<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.*" %>
<% PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
   BoardVo bv = (BoardVo)request.getAttribute("bv");
%>
<%@ include file="../include/header.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>

<style type="text/css">
		.fileDrop {
		width : 100%;
		height: 50px;
		border:1px dotted blue;
		}
		
		.uploadList {
		/* height: 30px;
		border: 0.5px solid;
		 */}
		
		small {
	margin-left: 3px;
	font-weight: bold;
	color: gray;
}
</style>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>

<script type="text/javascript" >
function check() {	
	  
	  var formname = document.frm;
	  
	  if (formname.subject.value =="") {
		  alert("제목입력하세요");
		  formname.subject.focus();	  
		  return ;	  
	  } else if (formname.content.value ==""){	
		  alert("내용 입력하세요");
		  formname.content.focus();	  
		  return ;
	  } else if (formname.writer.value ==""){	
		  alert("작성자 입력하세요");
		  formname.writer.focus();	  
		  return ;
	  } else if (formname.password.value ==""){	  
		  alert("비밀번호입력하세요");
		  formname.password.focus();	  
		  return ;
	  } 
	    var res;
	  	res = confirm("등록하시겠습니까?");
	  
	  	if (res == true) {
		   	formname.method ="post";
		   	formname.action ="<%=request.getContextPath() %>/BoardWriteActionController";
		   	formname.submit();  
	  	}
	  	return ;
	}	

	function addFilePath(msg){
		alert(msg);	
	}


 $(document).ready(function(){  //이미지나 파일 드레그앤 드랍
		
		$(".fileDrop").on("dragenter dragover",function(event){
			event.preventDefault();		
		});
		
		$(".fileDrop").on("drop",function(event){
			event.preventDefault();
			alert("들어옴");
			
		 	var files = event.originalEvent.dataTransfer.files;
			var file = files[0];
			
			var formData = new FormData(); //폼태그를 쓴것처럼 사용 가능 
											//폼태그와 같은 타입으로 멀티파트 폼데이터 타입으로 데이터 전송 가능
			
		//	console.log(file);
			 formData.append("file",file);
			
			$.ajax({
				url:'/uploadAjax',
				data: formData,
				dataType:'text',
				processData:false,//진행한 데이터
				contentType:false,
				type:'POST',
				success : function(data){
					alert(data);	//값 찍어보면 오리지널 네임이 나와야함
					
				//$("#uploadedList").html("<img src = '/resources/images/"+data+"'>");
				
				$("#uplodadfile").val(data.replace(/s-/,""));
				
				var str = "";
				
					
			/* 	
				console.log(data);
				 console.log(checkImageType(data)); 
			 */ 
				if (checkImageType(data)){
					str = "<div>"
						+ "<a href='displayFile?fileName="+getImageLink(data)+"'>"
						+ "<img src ='displayFile?fileName="+data+"'/>"
						+ "</a> <small data-src ="+data+">⒳</small></div>";
						
				} else {
					str = "<div><a href='displayFile?fileName="+data+"'>"
						+getOriginalName(data)+"</a>"
						+"<small data-src ="+data+">⒳</small></div>";
				}
				$(".uploadedList").append(str);
				}		
				
			});	 	 
			
		});
		

		$(".uploadedList").on("click","small",function(event){
			//alert("asdfafdafadf");
			var that = $(this);
			
			$.ajax({
				url:"deleteFile",
				type:"post",
				data : {fileName : $(this).attr("data-src")},
				dataType:"text",
				success: function (result) {
					if(result == 'deleted'){
						//alert("deleted");
						that.parent("div").remove();
					}
				}
			});
		});

 	}); 
	 
 function checkImageType(fileName) {
	var pattern = /jpg$|gif$|png$|jpeg$/i;
	return fileName.match(pattern);
}

function getOriginalName(fileName){
	if (checkImageType(fileName)){
		return;
	}
	var idx = fileName.indexOf("_")+1;
	return fileName.substr(idx);
}

function getImageLink(fileName){
	if (! checkImageType(fileName)){
		return;	
	}
	var front = fileName.substr(0,12);
	var end = fileName.substr(14);
	return front + end;
} 



</script>
</head>
<body>


<form name="frm" enctype="multipart/form-data" >
<input type=hidden name="page" value="<%=pm.getScri().getPage() %>" />
<input type=hidden name="searchType" value="<%=pm.getScri().getSearchType() %>" />
<input type=hidden name="keyword" value="<%=pm.getScri().getKeyword() %>" />
<table border=1 width="100%" height="600px">
<!-- <input type="hidden" id="uplodadfile" name="uplodadfile" /> -->
<tr>
<td>제목</td>
<td>
<input type="text" name="subject" id="subject" size="20" maxlength="20" />
</td>
</tr>
<tr>
<td>내용</td>
<td>
<textarea name="content" rows=20 cols=100 ></textarea>
</td>
</tr>
<%--  <%=bv.getFilename() %> --%>


<tr>
<td style="  height: 160px; ">첨부파일</td>
<td>
<input type="file" name="uploadfile">
<div class="fileDrop"></div>
</td>
</tr>

<tr>
<td>작성자</td>
<td>
<input type="text" name="writer"  size="20"  />
</td>
</tr>
<tr>
<td>비밀번호</td>
<td>
<input type="password" name="password" size="10" maxlength="10" />
</td>
</tr>

<tr>
<td></td>
<td align="center">
<input type="button" name="button" value="확인" onclick="javascript:check();" />
<input type="button" value="리셋" onclick="reset();"/>
<input type="button" value="목록" onclick="javascript:document.location.href='<%=request.getContextPath()%>/BoardListController';"/>
</td>
</tr>

<!-- 첨부파일 드래그 하면 내용 나타나는 곳 -->
<tr>
<td colspan="2">
<div class="uploadedList"></div></td>
</tr>
</table>
</form>


</body>
<%@include file="../include/footer.jsp" %>
