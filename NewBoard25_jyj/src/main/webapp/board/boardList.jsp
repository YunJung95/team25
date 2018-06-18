<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.myin.team25.domain.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><!-- jstl 사용시 추가해줘야함 -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../include/header.jsp" %>
<%
	String smemberId = "";	
    if (session.getAttribute("sMemberId") != null) {
	smemberId = (String)session.getAttribute("sMemberId");
    }	
    
	//ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");	
	//PageMaker pm = (PageMaker)request.getAttribute("pageMaker");
%> 
<c:set value="${pageMaker }" var="pm"> </c:set>



<table border="1" width="100%" style="text-align:center;">
 <tr>
 <td><h1> 목록보기 &nbsp;&nbsp;&nbsp;&nbsp;
 <a href="${pageContext.request.contextPath }/MemberListController"> 회원정보</a>&nbsp;&nbsp;&nbsp;&nbsp;
 <a href="${pageContext.request.contextPath }/BoardMemberInfoController"> 게시글 작성 회원정보</a> </h1> </td> 
 </tr>
 </table>
 <form name="searchform" action="${pageContext.request.contextPath }/BoardListController" method="post">
 <table align="right">
 <tr>
 <td>
 <select name="searchType">
 <option value="subject">제목</option>
 <option value="writer">작성자</option>
 </select>
 </td>
 <td>
 <input type="text" name="keyword" size="12" maxlength="12">
 </td>
 <td>
 <input type="submit" name="submit" value="검색" />
 </td>
 </tr>
 </table>
 </form>
 
 
 <table border="1" width="100%" style="text-align:center;">
 <tr style="text-align:center;background-color:#ffddfb;">
 <td>글번호</td>
 <td width="40%">제목</td> 
 <td>작성자</td>
 <td>작성일</td> 
 </tr> 
 
<tr>
	<c:set var="num" value="${pm.totalCount-((pm.scri.page-1)*pm.scri.perPageNum)}"></c:set>
	<c:forEach var="bvo" items="${alist}" >

	<td>${num}</td>
		<c:set var="num" value="${num - 1}"></c:set>
	 
	<td align="left">
	<%-- <c:forEach var="i" begin="1" end=" ${bvo.leftright}" step="1">
	&nbsp;&nbsp;
	<c:if test="${i == bvo.leftright}">
	<c:out value=">" />
	</c:if> leftright가 다 0값인데 0을 넣으면 오류가 나고 다른숫자를 넣으면 실행됨..
	</c:forEach>--%>

	

	<a href="${pageContext.request.contextPath }/BoardContentController${ pm.makeSearch(pm.scri.page)}&bbidx=${bvo.bbidx}">
	
	
	<c:set var="subject222" value="${bvo.subject }" />
	<c:set var="writer2222" value="${bvo.writer }" />
	<c:set var="keyword222" value="${pm.scri.keyword }" />
	<c:set var="red" value="<span style='color:red;font-weight:bold'>${pm.scri.keyword }</span>" />
<!-- 제목 검색부분 -->
	<c:choose> 
		<c:when test="${pm.scri.searchType != null }">
			<c:choose>
				<c:when test="${pm.scri.searchType eq 'subject' }">
				<c:out value="${fn:replace(subject222, keyword222, red)}" escapeXml="false"/>
				</c:when>
				<c:otherwise>
				${bvo.subject}
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			${bvo.subject}
		</c:otherwise>
	</c:choose>
	</a>
	</td>	
	
	<td>
	<!-- 작성자 검색하는 부분  -->
	<c:choose>  
		<c:when test="${pm.scri.searchType != null }">
			<c:choose>
				<c:when test="${pm.scri.searchType eq 'writer' }">
				<c:out value="${fn:replace(writer2222, keyword222, red)}" escapeXml="false"/>
				</c:when>
				<c:otherwise>
				${bvo.writer}
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			${bvo.writer}
		</c:otherwise>
	</c:choose>
	
	
	</td>
	<td>${bvo.writeday}</td>		
	</tr>


 </c:forEach>
 </table> 
 
 <div style="text-align: center;">
	 <div class="jb-center">
		 <ul class="pagination">
		 	<li><!-- Prev(이전) 이전으로 넘기는 화살표 -->
		 	
 			<c:if test="${pm.prev == true }">
 			<a href="${pageContext.request.contextPath}/BoardListController${pm.makeSearch(pm.startPage-1)}"> 			
		 			<span class="glyphicon glyphicon-chevron-left"></span>
	 			</a> 
 			</c:if>		
			</li>
			
			
 	 	<!-- 가운데에 나오는 숫자 -->
 	 
           
          <c:forEach var="i"  begin ="${ pm.startPage}"  end="${pm.endPage }" step="1"> 
	          <li <c:if test="${pm.next && pm.endPage > 0}" />>     
	           		<a href="${pageContext.request.contextPath }/BoardListController${pm.makeSearch(i)}">${i}</a>
	           </li>  
          </c:forEach>
  
		 	<li><!-- 다음으로 넘기는 화살표 -->
		 
 			<c:if test="${pm.next && pm.endPage > 0}">
 			<a href="${pageContext.request.contextPath}/BoardListController${pm.makeSearch(pm.endPage+1)}"> 			
		 			▷
	 			</a> 
 			</c:if>		
			</li>
		 </ul>
	 </div>
 </div> 
 
 <center>
 <h2>
 <a href="${pageContext.request.contextPath}/BoardWriteController${pm.makeSearch(pm.scri.page)}"> 글쓰기</a> 
 </h2>
 </center>

<%@include file="../include/footer.jsp" %>












