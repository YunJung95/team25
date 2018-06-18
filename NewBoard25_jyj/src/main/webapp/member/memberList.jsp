<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.myin.team25.domain.MemberVo"  %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<%@include file="../include/header.jsp" %>

<script>
function Modify(){	
	document.location.href='<%=request.getContextPath()%>/MemberModifyController';	
	return;
}
</script>

<!-- if문,else문    -->
<c:choose> 
<c:when test="${sMemberId != null }">

<c:set var="smemberId" value="sMemberId"></c:set>
<c:out value="${sMemberId}님 안녕하세요"></c:out>

<a href ="${pageContext.request.contextPath }/MemberLogoutController">로그아웃</a>
<input type='button' name='button' value='회원정보수정' onclick='Modify();' />

</c:when>
<c:otherwise>

<c:out value="로그인 해주셈"></c:out>
<a href ="${pageContext.request.contextPath }/MemberLoginController">로그인하기</a>
<br>
<a href ="${pageContext.request.contextPath }/MemberJoinController">회원가입하기</a>
</c:otherwise>


</c:choose>


<!-- 위에 jstl 방식으로 바꿔줌 	
	String smemberId = null;	
    if (session.getAttribute("sMemberId") != null) {
		smemberId = (String)session.getAttribute("sMemberId");
    }
    
	
	if (smemberId == null){
	out.println("로그인 하세요");
	out.println("<a href ='"+request.getContextPath()+"/MemberJoinController'>회원가입하기</a>");
	}else{
	out.println(smemberId+"님 안녕하세요");
	out.println("<a href ='"+request.getContextPath()+"/MemberLogoutController'>로그아웃</a>"+
				"<input type='button' name='button' value='회원정보수정' onclick='Modify();' />");
	}
	
	//ArrayList<MemberVo> alist =  (ArrayList<MemberVo>)request.getAttribute("alist");	
	  -->
 
<h1>회원정보 리스트 &nbsp;&nbsp; 
<c:if test="${smemberId != null }" >

<a href="${pageContext.request.contextPath }/BoardListController">글목록</a>

</c:if>
 </h1>
 <table border="1" width="100%" style="text-align:center;">
 <tr>
 <td>회원번호</td>
 <td>아이디</td>
 <td>비밀번호</td>
 <td>이름</td>
 <td>성별</td>
 <td>주민번호</td>
 <td>주소</td>
 <td>이메일</td>
 <td>IP</td>
 <!-- <td>부서</td>
 <td>가입일</td>
 <td>수정일</td> -->
 </tr> 


<c:forEach var="mvo" items="${alist }"> 
<tr>
<td>${mvo.memberMidx}</td>
<td>${mvo.memberId}</td>
<td>${mvo.memberPassword}</td>
<td>${mvo.memberName}</td>
<td>${mvo.memberSex}</td>
<td>${mvo.memberJumin}</td>
<td>${mvo.memberAddr}</td>
<td>${mvo.memberEmail}</td>
<td>${mvo.memberIp}</td>
<%-- <td>${mvo.bidx}</td>
<td>${mvo.writeDay}</td>
<td>${mvo.modifyDay}</td>		 --%>	
</tr>	
 
 </c:forEach>
 
 <%-- 
 <% //for (MemberVo mvo : alist ) { %>
<c:forEach var="mvo" items="${alist }"> 
<tr>
<td>${mvo.memberMidx}<%=//mvo.getMemberMidx() %></td>
<td>${mvo.memberId}<%=//mvo.getMemberId() %></td>
<td>${mvo.memberPassword}<%=//mvo.getMemberPassword() %></td>
<td>${mvo.memberName}<%=//mvo.getMemberName() %></td>
<td>${mvo.memberSex}<%=//mvo.getMemberSex() %></td>
<td>${mvo.memberJumin}<%=//mvo.getMemberJumin() %></td>
<td>${mvo.memberAddr}<%=//mvo.getMemberAddr() %></td>
<td>${mvo.memberEmail}<%=//mvo.getMemberEmail() %></td>
<td>${mvo.memberIp}<%=//mvo.getMemberIp() %></td>
<td>${mvo.bbidx}<%=//mvo.getBidx() %></td>
<td>${mvo.writeDay}<%=//mvo.getWriteday() %></td>
<td>${mvo.modifyDay}<%=//mvo.getModifyday() %></td>			
</tr>	
 <% //} %> --%>
 </table>
 
 <div style="text-align: center;">
	 <div class="jb-center">
		 <ul class="pagination">
		 	<li>
 			<span class="glyphicon glyphicon-chevron-left">
 			</span> 		
			</li>
 	 	
 	 	  <% for(int i=1; i<=10;i++) { %>
           <li>     
           <a href="#"><%=i %></a>
           </li>              
          <% } %>
  
		 	<li>
 			<span class="glyphicon glyphicon-chevron-right">
 			</span>
			</li>
		 </ul>
	 </div>
 </div>
 
 <%@include file="../include/footer.jsp" %>
