<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.myin.team25.domain.*" %>

<%@include file="../include/header.jsp" %>

<%
	String smemberId = "";	
    if (session.getAttribute("sMemberId") != null) {
	smemberId = (String)session.getAttribute("sMemberId");
    }	
    
    BoardVo bv  = (BoardVo)request.getAttribute("bv"); 
    int sMemberMidx = (int)session.getAttribute("sMemberMidx");
	ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");	
	PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
%> 


<script type="text/javascript">
$(function(){	//윈도우를 실행하자마자 실행됨	(처음에 자동실행됨)
	//alert("ddd");
 	$.boardCommentList(); //호출 
	
});
                   		//Ajax으로 만든 것
$.boardCommentList = function() {	//삭제하고 리스트를 보내줄때마다 이거 사용하면 됨
	//alert("왓냐");
	
	$.ajax({	
		type : "GET", //리스트니까 겟 방식으로 넘어감 
	//변수 : 값
		url  : "/BoardListAjaxController", //json의 위치 (가상경로) 해당되는 URL 
		datatype : "text",
		cache : false,
		error : function(){				
		alert("error3");
		},
		success : function(data){	
			//alert("213123123");
		commentList(data); //뿌려줘야 할것들
		}			
	});	
}
 
function commentList(data){ //자바스크립트로 만듬
	var str = '';						
	
	$(data).each(function(){//each반복문 
	//alert(this.bbidx);	
		
		// i =+ 1; i=1; str += tr; str=trtrtr;
		str += "<tr>"
			+" <td>"+ this.bbidx +"</td> "
			+" <td>"+ this.subject +"</td> "
			+" <td>"+ this.writer +"</td>"
			+" <td>"+ this.writeday +"</td>"
			+" </tr>";					
	});
	//tbl 어딘가에 뿌려줄려고 하는 위치
	$('#tbl').html("<table border='1' width='100%' style='text-align:center;'> "
			+"<tr style='text-align:center;background-color:#ffddfb;'>"
			+" <td>글번호</td> "
			+" <td>제목</td> "
			+" <td>작성자</td>"
			+" <td>작성일</td>"
			+" </tr> "+str+" </table>");	
							

}
</script>




<table border="1" width="100%" style="text-align:center;">
 <tr>
 <td><h1> 목록보기 &nbsp;&nbsp;&nbsp;&nbsp;
 <a href="<%=request.getContextPath()%>/MemberListController"> 회원정보</a>&nbsp;&nbsp;&nbsp;&nbsp;
 <a href="<%=request.getContextPath()%>/BoardListController"> 게시글 목록</a> </h1> </td> 
 </tr>
 
 
 </table>
 
 <div id="tbl"></div>
 <% // 토탈갯수를 구해서 페이지마커에잇는 서치크리안에   
//페이지 숫자를 임의로 만드는거
int num =pm.getTotalCount()-((pm.getScri().getPage()-1)*pm.getScri().getPerPageNum());

for (BoardVo bvo : alist )
{ %>
	<tr>
	<td><%=num%></td>
	<td style="text-align:left;">
	&nbsp;&nbsp;	
	<% 	//답글에 > 붙이는 것
	for (int i=1;i<=bvo.getLeftright();i++){
		out.print("&nbsp;&nbsp;");
		if (i == bvo.getLeftright()){
			out.print(">");
		}
	} 
	%>	
	<a href="<%=request.getContextPath() %>/BoardContentController<%=pm.makeSearch(pm.getScri().getPage())%>&bbidx=<%=bvo.getBbidx()%>">
	<% // 제목 검색부분
	if (pm.getScri().getSearchType() != null){
		if (pm.getScri().getSearchType().equals("subject")){
			out.println(bvo.getSubject().replaceAll(pm.getScri().getKeyword(), "<span style='color:red;font-weight:bold'>"+pm.getScri().getKeyword()+"</span>"));
		}else{
			out.println(bvo.getSubject());
		}
	}else{
		out.println(bvo.getSubject());
	}
	%>		
	</a>
	</td>	
	<td>
	<% // 작성자 검색하는 부분  
	if (pm.getScri().getSearchType() != null){
		if (pm.getScri().getSearchType().equals("writer")){
			out.println(bvo.getWriter().replaceAll(pm.getScri().getKeyword(), "<span style='color:red;font-weight:bold'>"+pm.getScri().getKeyword()+"</span>"));
		}else{
			out.println(bvo.getWriter());
		}
	}else{
		out.println(bvo.getWriter());
	}
	%>	
	</td>
	
	<td><%=bvo.getWriteday() %></td>		
	</tr>	
<% 
	num = num -1;
	} 
%>
 </table> 
 <table>
 <div style="text-align: center;">
	 <div class="jb-center">
		 <ul class="pagination">
		 	<li><!-- Prev(이전) 이전으로 넘기는 화살표 -->
		 	<%if (pm.isPrev() == true) { %>
	 			<a href="<%=request.getContextPath() %>/BoardListController<%=pm.makeSearch(pm.getStartPage()-1)%>"> 			
		 			<span class="glyphicon glyphicon-chevron-left"></span>
	 			</a> 		
 			<% } %> 			
			</li>
 	 	<!-- 가운데에 나오는 숫자 -->
 	 	  <% for(int i= pm.getStartPage(); i<=pm.getEndPage(); i++) { %>
           <li <% if (pm.getScri().getPage() == i){ %>class="active" <% } %>>     
           		<a href="<%=request.getContextPath() %>/BoardListController<%=pm.makeSearch(i)%>"><%=i %></a>
           </li>              
          <% } %>
  
		 	<li><!-- 다음으로 넘기는 화살표 -->
		 	<% if (pm.isNext() && pm.getEndPage() >0) { %>
 			<a href="<%=request.getContextPath() %>/BoardListController<%=pm.makeSearch(pm.getEndPage()+1)%>">
	 			<span class="glyphicon glyphicon-chevron-right"></span>
 			</a>
 			<% } %>
			</li>
		 </ul>
	 </div>
 </div> 
 
 <center>
 <h2>
 <a href="<%=request.getContextPath()%>/BoardWriteController<%=pm.makeSearch(pm.getScri().getPage())%>"> 글쓰기</a> 
 </h2>
 </center>

<%@include file="../include/footer.jsp" %>












