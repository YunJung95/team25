<%@page import="java.net.URLEncoder"%>
<%@page import="sun.awt.image.URLImageSource"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.*" %>
<%@ include file="../include/header.jsp" %>
<% 
BoardVo bv  = (BoardVo)request.getAttribute("bv"); 
PageMaker pm = (PageMaker)request.getAttribute("pageMaker");

int sMemberMidx = 0;		//if문으로 로그인 안해도 들어가게끔 만듬
if (session.getAttribute("sMemberMidx") != null){
sMemberMidx = (int)session.getAttribute("sMemberMidx"); 
}
%>

<!--  jquery 사용하기위한  jquery cdn주소는 header.jsp에 들어있음 -->
<script type="text/javascript">
$(function(){	//윈도우를 실행하자마자 실행됨	(처음에 자동실행할때)
	
	$.boardCommentList();
	
	//window.onlord()자바스크립트에서는 이케 윈도우가 딱뜰때 실행되는 함수? <body onload="메소드">html방식	
		 <%-- $.ajax({											//$(doqument).ready(); 자동으로 실행되는 함수 ()안에잇는 메소드를 윈도우 실행하자마자 실행됨
		 													//$(function()) 이게 줄여서 사용하는거 
		 	
			type : "GET",//url에 나타나며 넘어감 넘길때 보이면서 넘김 
			url  : "/comments/all/<%=bv.getBbidx()%>", //json의 위치 (가상경로) 해당되는 URL
			datatype : "text",
			cache : false,
			error : function(){				
				alert("error");
			},
			success : function(data){			
				commentList(data);
				}			
		});	  --%>
		
		//더보기 기능
	 $('#more').click(function(){ //버튼을 누르면 20개의 리스트가 나옴 한번더 누르면 30개 +10씩
			
			 //#(아이디)nextBlock의 값의 valu값
		var block = $("#nextBlock").val(); //값 꺼내 쓰기위해 
				alert(block);
				//alert(data.);
				
 
			 $.ajax({											
					type : "GET",
					url  : "/comments/"+block+"/<%=bv.getBbidx()%>", //json의 위치 (가상경로) 해당되는 URL
					datatype : "text",
					cache : false,
					error : function(){				
					alert("error1");
					},
					success : function(data){	//alist와 nextBlock 값이 받아짐
						//alist와 nextBlock 나온다				
										//attr 속성?		
												//맨첨2값을 받음 또 누르면 3이 나옴
					$("#nextBlock").val(data.nextBlock); //벨류 값을 담는다.
						
					commentList(data.alist);
					

					/* if(data.length == undefined) { //다음 데이터 값이 없으면
					    $('#more').remove(); //more 버튼을 지워라
					}
					  */
					
					//console.log(data.length);
					}			
				});	
			 
		 });
		 
		 
		 
		 $('#save').click(function(){		 //저장했을때
				
			 var bbidx = $("#bbidx").val();	
			 var cwriter = $("#cwriter").val();
			 var ccontent = $("#ccontent").val();//값 불러오기?
			 
			 $.ajax({
					type : "POST",//숨겨서 넘어감 URL에 안나타남 넘길때 감춰서 넘김
					url  : "/comments",
					headers : { //지정해줬다
						"Content-Type" : "application/json"//,
						//"X-HTTP-Method-Override" : "POST"  delete
					}, 
					//datatype : "TEXT", //text, json등등 지정 가능success에서 data로 들어오는애 타입
					data : JSON.stringify({			//json으로 바꿔서 넘김
						bbidx : bbidx,
						cwriter : cwriter,
						ccontent : ccontent// 값들을 꺼재서 json파일 형태로 데이터를 넘기는 
					}),
					cache : false, //여유공간
					error : function(){		//에러나면 이 함수 작동		
						alert("error2");
					},
					success : function(data){ // 성공하면 이 함수 실행  entity값, requst값이 나옴
						
						alert(data);
							//등록 메세지와 헤더값을 기대함 값을 받는걸 확인하고 리스트 출력 	
						$.boardCommentList();
							
						 //$("#bbidx").val("");	 이건 게시물 번호니까 초기화해주면 안됨!!!!!
						 $("#cwriter").val("");
						 $("#ccontent").val(""); //값 초기화
							
							}			
				});	
		});			 
});



//삭제
$.del = function(cidx){
	//alert("삭제들어옴");
	var cidx = cidx;		
	var bbidx = <%=bv.getBbidx()%>;
	//alert(bbidx);
	//alert(cidx);
	
	 $.ajax({
			type : "GET",
			url  : "/comments/del/"+cidx+"/"+bbidx,	 //레스트 방식 	
			datatype : "text",				
			cache : false,
			error : function(){				
				alert("error4");
			},
			success : function(data){			
					//메세지와 헤더값을 기대함
				if (data == null){
					alert("데이타없음4");
				}
				$.boardCommentList();
				
			}			
			});	
}	

$.boardCommentList = function() { //삭제하고 리스트를 보내줄때마다 이거 사용하면 됨

	$.ajax({											
		type : "GET",
		url  : "/comments/all/<%=bv.getBbidx()%>", //json의 위치 (가상경로) 해당되는 URL
		datatype : "text",
		cache : false,
		error : function(){				
		alert("error3");
		},
		success : function(data){	
			
		commentList(data);
		}			
	});	
}

function commentList(data){
	var str = '';						
	var sMemberMidx = <%=sMemberMidx%>;
	
	
	$(data).each(function(){//each반복문 
		
		var delinput = "";
		if (sMemberMidx == this.memberMidx) {		//삭제버튼 
			delinput ="<li class='sub4'><button class='btn btn-danger' onclick='$.del("+this.cidx+")'>삭제</button></li>";
			}
		
		str += "<ul><li class='sub1'>"+this.cidx   + "</li>" 
			+  "<li class='sub2'>"+this.cwriter + "</li>"
			+  "<li class='sub3'>"+this.ccontent  + "</li>"
			+  delinput
			+  "</ul>";					
	});
	
	$('#tbl').html("<ul><li class='title1'>번호</li>"
					+"<li class='title2'>작성자</li>"
					+"<li class='title3'>내용</li>"
					+"<li class='title4'>삭제여부</li>"
					+"</ul>"+str+"");				

}
</script>

<div class="box-body">
<h1>내용보기</h1>
<br>
	<div class="form-group">
	<label for="examInputEmail1">제목 <%=bv.getFilename() %> </label>
	<input type="text" class="form-control" value="<%=bv.getSubject() %>" readonly="readonly" />
	</div>
	
	<div class="form-group">
	<label for="examInputPassword1">내용 </label>
	<textarea class="form-control" rows="3" readonly="readonly">
	<%=bv.getContent() %>
	</textarea>	  
	</div>
	
	<div class="form-group">
	<label for="examInputEmail1">첨부파일 </label>
	<a href=displayFile?flieName=<%= URLEncoder.encode(bv.getFilename(),"UTF-8") %>> <%= bv.getFilename() %> </a>
	<img src ='displayFile?fileName=<%= bv.getFilename() %>' />
	</div>
	
	<div class="form-group">
	<label for="examInputEmail1">작성자 </label>
	<input type="text" class="form-control" value="<%=bv.getWriter() %>" readonly="readonly" />
	</div>
	
	<div class="form-group">
	<label for="examInputEmail1">작성일 </label>
	<input type="text" class="form-control" value="<%=bv.getWriteday() %>" readonly="readonly" />
	</div>
</div>

<div class="box-footer">
	<div class="form-group" style="float:right;">
		<label for="examInputEmail1">
		<a href="<%=request.getContextPath()%>/BoardModifyController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bv.getBbidx()%>">수정</a>&nbsp;
		<a href="<%=request.getContextPath()%>/BoardDeleteController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bv.getBbidx()%>">삭제</a>&nbsp; 
		<a href="<%=request.getContextPath()%>/BoardReplyController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bv.getBbidx()%>&oidx=<%=bv.getOidx()%>&updown=<%=bv.getUpdown()%>&leftright=<%=bv.getLeftright()%>">답변</a>&nbsp; 
		<a href="<%=request.getContextPath()%>/BoardListController<%=pm.makeSearch(pm.getScri().getPage())%>">목록</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</label>
	</div>
</div>
<div id="commenttbl" class="box-body">
	<div class="form-group">
	<label for="examInputEmail1">작성자</label>
	<input type="text" class="form-control" name="cwriter" id ="cwriter" placeholder="이름입력하세요" />
	<input type="hidden" name="bbidx" id="bbidx"  value="<%=bv.getBbidx()%>" />	
	<label for="examInputEmail1">내용</label>
	<textarea class="form-control" name="ccontent" id="ccontent" rows="3" placeholder="내용입력하세요"></textarea>
	</div>
	
	<div class="form-group">
	<input type="button" name="save" id="save" value="저장" class="comment_input" />	
	</div>
</div>

<div id="tbl"></div>
&nbsp;
<div class="form-group">
<center>
<button id='more' class='btn btn-primary'>더보기</button>
</center>	
<input id='nextBlock' type='hidden' value='2' />
</div>
<%@include file="../include/footer.jsp" %>