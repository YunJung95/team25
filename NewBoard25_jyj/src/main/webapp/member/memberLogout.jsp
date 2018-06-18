<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그아웃페이지</title>
</head>
<body>

로그아웃 되었습니다.
<%-- 
<a href="<%=request.getContextPath()%>/"> 메인가기</a>
 --%>
<a href="${pageContext.request.contextPath }/"> 메인가기</a>


</body>
<%@include file="../include/footer.jsp" %>