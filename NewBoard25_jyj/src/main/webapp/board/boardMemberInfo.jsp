<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.myin.team25.domain.*" %>
<%@ page import="java.util.HashMap" %>

<%@include file="../include/header.jsp" %>
<%
	String smemberId = "";	
    if (session.getAttribute("sMemberId") != null) {
	smemberId = (String)session.getAttribute("sMemberId");
    }	
    
	ArrayList<HashMap<String,Object>> alist = (ArrayList<HashMap<String,Object>>)request.getAttribute("alist");	
	PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
	
%> 
<table border="1" width="100%" style="text-align:center;">
 <tr>
 <td><h1>게시물 작성자 회원정보 보기 &nbsp;&nbsp;
 <a href="<%=request.getContextPath()%>/BoardListController">글목록</a>
  </h1> </td> 
 </tr>
 </table>
 
 <table border="1" width="100%" style="text-align:center;">
 <tr style="text-align:center;background-color:green;">
 <td>글번호</td>
 <td>게시글번호</td> 
 <td>회원이름</td>
 <td>나이</td>
 <td>ip</td> 
 </tr> 

 <% int num = 0;
// num = pm.getTotalCount()-((pm.getScri().getPage()-1)*pm.getScri().getPerPageNum());

for (HashMap<String,Object> map : alist )
{ %> 
	<tr>
	<td><%=num%></td>
	<td style="text-align:left;">
	<%=map.get("BBIDX") %>	
	</td>	
	<td>
	<%=map.get("MEMBERNAME") %>	<!-- 대소문자 구분함! 조심해야댐! -->
	</td>	
	<td><%=map.get("AGE") %></td>
	
	<td><%=map.get("IP") %></td> 		
	</tr>	
 <% 
	num = num +1;
	} 
%> 
 </table>  
 
 
 
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
<%--  <h2>
 <a href="<%=request.getContextPath()%>/BoardWriteController<%=pm.makeSearch(pm.getScri().getPage())%>"> 글쓰기</a> 
 </h2>

 --%>
<%@include file="../include/footer.jsp" %>